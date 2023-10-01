package cn.dlmyl.xa.account.service;

import cn.dlmyl.xa.account.mapper.XaAccountMapper;
import cn.dlmyl.xa.account.model.XaAccount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * XA-Account Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
public class XaAccountService extends ServiceImpl<XaAccountMapper, XaAccount> {

    @Transactional(rollbackFor = Exception.class)
    public void reduce(String userId, int money) {

        String xid = RootContext.getXID();
        log.info("reduce account balance in transaction: " + xid);

        XaAccount account = lambdaQuery().eq(XaAccount::getUserId, userId).one();
        if (account != null && account.getMoney() < money) {
            throw new RuntimeException("账户余额不足！");
        }
        lambdaUpdate().setSql("money = money - " + money)
                .eq(XaAccount::getUserId, userId)
                .update();
    }

}
