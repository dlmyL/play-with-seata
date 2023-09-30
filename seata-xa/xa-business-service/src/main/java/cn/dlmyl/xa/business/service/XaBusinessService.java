package cn.dlmyl.xa.business.service;

import cn.dlmyl.xa.business.common.Constants;
import cn.dlmyl.xa.business.feign.XaOrderFeignClient;
import cn.dlmyl.xa.business.feign.XaStockFeignClient;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * XA-Business Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class XaBusinessService {

    private final XaStockFeignClient stockFeignClient;
    private final XaOrderFeignClient orderFeignClient;

    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount, boolean rollback) {
        String xid = RootContext.getXID();
        log.info("New Transaction Begins: {}", xid);

        String result = stockFeignClient.deduct(commodityCode, orderCount);

        if (!Constants.SUCCESS.equals(result)) {
            throw new RuntimeException("库存服务调用失败，事务回滚");
        }

        result = orderFeignClient.create(userId, commodityCode, orderCount);

        if (!Constants.SUCCESS.equals(result)) {
            throw new RuntimeException("订单服务调用失败，事务回滚");
        }

        if (rollback) {
            throw new RuntimeException("Force rollback... ");
        }
    }

}
