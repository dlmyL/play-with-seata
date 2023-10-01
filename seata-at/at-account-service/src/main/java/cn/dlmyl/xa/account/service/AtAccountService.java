package cn.dlmyl.xa.account.service;

import cn.dlmyl.xa.account.mapper.AtAccountMapper;
import cn.dlmyl.xa.account.model.AtAccount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AT-Account Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
public class AtAccountService extends ServiceImpl<AtAccountMapper, AtAccount> {

    @Transactional(rollbackFor = Exception.class)
    public void reduce(String userId, int money) {

        String xid = RootContext.getXID();
        log.info("reduce account balance in transaction: " + xid);

        AtAccount account = lambdaQuery().eq(AtAccount::getUserId, userId).one();
        if (account != null && account.getMoney() < money) {
            throw new RuntimeException("账户余额不足！");
        }
        lambdaUpdate().setSql("money = money - " + money)
                .eq(AtAccount::getUserId, userId)
                .update();
    }

}
