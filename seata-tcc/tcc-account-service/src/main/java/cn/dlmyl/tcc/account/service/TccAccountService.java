package cn.dlmyl.tcc.account.service;

import cn.dlmyl.tcc.account.mapper.TccAccountMapper;
import cn.dlmyl.tcc.account.model.TccAccount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TCC-Account Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
public class TccAccountService extends ServiceImpl<TccAccountMapper, TccAccount> {

    @Transactional(rollbackFor = Exception.class)
    public void reduce(String userId, int money) {

        String xid = RootContext.getXID();
        log.info("reduce account balance in transaction: " + xid);

        TccAccount account = lambdaQuery().eq(TccAccount::getUserId, userId).one();
        if (account != null && account.getMoney() < money) {
            throw new RuntimeException("账户余额不足！");
        }
        lambdaUpdate().setSql("money = money - " + money)
                .eq(TccAccount::getUserId, userId)
                .update();
    }

}
