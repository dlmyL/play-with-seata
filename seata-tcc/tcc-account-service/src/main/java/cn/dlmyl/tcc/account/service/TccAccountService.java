package cn.dlmyl.tcc.account.service;

import cn.dlmyl.tcc.account.mapper.TccAccountMapper;
import cn.dlmyl.tcc.account.mapper.TccAccountTxMapper;
import cn.dlmyl.tcc.account.model.TccAccount;
import cn.dlmyl.tcc.account.model.TccAccountTx;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * TCC-二阶段提交业务接口
 *
 * @author dlmyL
 */
@Slf4j
@Service
@LocalTCC
@RequiredArgsConstructor
public class TccAccountService extends ServiceImpl<TccAccountMapper, TccAccount> {

    private final TccAccountMapper accountMapper;
    private final TccAccountTxMapper accountTxMapper;

    /**
     * 预扣款
     *
     * @param userId 用户ID
     * @param money  扣除的金额
     */
    @TwoPhaseBusinessAction(name = "tryReduce", commitMethod = "confirm", rollbackMethod = "cancel")
    public void tryReduce(@BusinessActionContextParameter(paramName = "userId") String userId,
                          @BusinessActionContextParameter(paramName = "money") int money) {

        System.err.println("------ tryReduce ------");

        String xid = RootContext.getXID();
        log.info("tryReduce account balance in transaction: " + xid);

        // 业务悬挂
        TccAccountTx accountTx = accountTxMapper.selectOne(
                new LambdaQueryWrapper<TccAccountTx>().eq(TccAccountTx::getTxId, RootContext.getXID()));
        // 存在，说明已经执行过cancel了，直接返回
        if (accountTx != null) {
            return;
        }

        TccAccount account = accountMapper.selectOne(
                new LambdaQueryWrapper<TccAccount>().eq(TccAccount::getUserId, userId));

        if (account != null && account.getMoney() < money) {
            throw new RuntimeException("账户余额不足！");
        }

        accountMapper.update(null, new LambdaUpdateWrapper<TccAccount>()
                .setSql("money = money - " + money).eq(TccAccount::getUserId, userId));

        TccAccountTx tx = new TccAccountTx();
        tx.setFreezeMoney(money);
        tx.setTxId(RootContext.getXID());
        tx.setState(TccAccountTx.STATE_TRY);
        accountTxMapper.insert(tx);
    }


    /**
     * 提交
     *
     * @param ctx The type Business action context
     * @return true or false
     */
    public boolean confirm(BusinessActionContext ctx) {
        System.err.println("------ confirm ------");

        // 删除记录
        int ret = accountTxMapper.delete(
                new LambdaQueryWrapper<TccAccountTx>().eq(TccAccountTx::getTxId, ctx.getXid())
        );
        return ret == 1;
    }

    /**
     * 回滚
     *
     * @param ctx The type Business action context
     * @return true or false
     */
    public boolean cancel(BusinessActionContext ctx) {
        System.err.println("------ cancel ------");

        String userId = Objects.requireNonNull(ctx.getActionContext("userId")).toString();
        String money = Objects.requireNonNull(ctx.getActionContext("money")).toString();

        TccAccountTx accountTx = accountTxMapper.selectOne(
                new LambdaQueryWrapper<TccAccountTx>().eq(TccAccountTx::getTxId, ctx.getXid()));
        // 空回滚
        if (accountTx == null) {
            accountTx = new TccAccountTx();
            accountTx.setTxId(ctx.getXid());
            accountTx.setState(TccAccountTx.STATE_CANCEL);
            if (money != null) {
                accountTx.setFreezeMoney(Integer.parseInt(money));
            }
            accountTxMapper.insert(accountTx);
            return true;
        }

        // 幂等处理
        if (accountTx.getState() == TccAccountTx.STATE_CANCEL) {
            return true;
        }

        // 恢复余额
        accountMapper.update(null, new LambdaUpdateWrapper<TccAccount>()
                .setSql("money = money + " + money).eq(TccAccount::getUserId, userId));

        accountTx.setFreezeMoney(0);
        accountTx.setState(TccAccountTx.STATE_CANCEL);
        int ret = accountTxMapper.updateById(accountTx);
        return ret == 1;
    }

}


