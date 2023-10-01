package cn.dlmyl.tcc.business.service;

import cn.dlmyl.tcc.business.common.Constants;
import cn.dlmyl.tcc.business.feign.TccOrderFeignClient;
import cn.dlmyl.tcc.business.feign.TccStockFeignClient;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TCC-Business Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TccBusinessService {

    private final TccStockFeignClient stockFeignClient;
    private final TccOrderFeignClient orderFeignClient;

    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount, boolean rollback) {
        String xid = RootContext.getXID();
        log.info("New Transaction Begins: {}", xid);

        String result = stockFeignClient.deduct(commodityCode, orderCount);

        if (!Constants.SUCCESS.equals(result)) {
            throw new RuntimeException("扣减库存失败，事务回滚");
        }

        result = orderFeignClient.create(userId, commodityCode, orderCount);

        if (!Constants.SUCCESS.equals(result)) {
            throw new RuntimeException("创建订单失败，事务回滚");
        }

        if (rollback) {
            throw new RuntimeException("Force rollback... ");
        }
    }

}
