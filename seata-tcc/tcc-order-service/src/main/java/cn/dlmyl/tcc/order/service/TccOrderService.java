package cn.dlmyl.tcc.order.service;

import cn.dlmyl.tcc.order.common.Constants;
import cn.dlmyl.tcc.order.feign.TccAccountFeignClient;
import cn.dlmyl.tcc.order.model.TccOrder;
import cn.dlmyl.tcc.order.mapper.TccOrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TCC-Order Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TccOrderService extends ServiceImpl<TccOrderMapper, TccOrder> {

    private final TccAccountFeignClient accountFeignClient;

    @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, int count) {
        String xid = RootContext.getXID();
        log.info("create order in transaction: {}", xid);

        // 订单总价 = 订购数量(count) * 商品单价(100)
        int orderMoney = count * 100;
        // 生成订单
        TccOrder order = TccOrder.builder()
                .userId(userId)
                .commodityCode(commodityCode)
                .count(count)
                .money(orderMoney)
                .build();
        super.save(order);
        // 调用账户余额扣减
        String result = accountFeignClient.reduce(userId, orderMoney);
        if (!Constants.SUCCESS.equals(result)) {
            throw new RuntimeException("调用账户服务失败");
        }
    }

}
