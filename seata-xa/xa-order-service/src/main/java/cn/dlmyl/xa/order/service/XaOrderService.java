package cn.dlmyl.xa.order.service;

import cn.dlmyl.xa.order.common.Constants;
import cn.dlmyl.xa.order.feign.XaAccountFeignClient;
import cn.dlmyl.xa.order.mapper.XaOrderMapper;
import cn.dlmyl.xa.order.model.XaOrder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * XA-Order Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class XaOrderService extends ServiceImpl<XaOrderMapper, XaOrder> {

    private final XaAccountFeignClient accountFeignClient;

    @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, int count) {
        String xid = RootContext.getXID();
        log.info("create order in transaction: {}", xid);

        // 订单总价 = 订购数量(count) * 商品单价(100)
        int orderMoney = count * 100;
        // 生成订单
        XaOrder order = XaOrder.builder()
                .userId(userId)
                .commodityCode(commodityCode)
                .count(count)
                .money(orderMoney)
                .build();
        super.save(order);
        // 调用账户余额扣减
        String result = accountFeignClient.reduce(userId, orderMoney);
        if (!Constants.SUCCESS.equals(result)) {
            throw new RuntimeException("Failed to call Account Service. ");
        }
    }

}
