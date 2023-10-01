package cn.dlmyl.xa.order.service;

import cn.dlmyl.xa.order.common.Constants;
import cn.dlmyl.xa.order.feign.AtAccountFeignClient;
import cn.dlmyl.xa.order.mapper.AtOrderMapper;
import cn.dlmyl.xa.order.model.AtOrder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AT-Order Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AtOrderService extends ServiceImpl<AtOrderMapper, AtOrder> {

    private final AtAccountFeignClient accountFeignClient;

    @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, int count) {
        String xid = RootContext.getXID();
        log.info("create order in transaction: {}", xid);

        // 订单总价 = 订购数量(count) * 商品单价(100)
        int orderMoney = count * 100;
        // 生成订单
        AtOrder order = AtOrder.builder()
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
