package cn.dlmyl.tcc.order.service;

import cn.dlmyl.tcc.order.common.Constants;
import cn.dlmyl.tcc.order.feign.TccAccountFeignClient;
import cn.dlmyl.tcc.order.mapper.TccOrderMapper;
import cn.dlmyl.tcc.order.mapper.TccOrderTxMapper;
import cn.dlmyl.tcc.order.model.TccOrder;
import cn.dlmyl.tcc.order.model.TccOrderTx;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * TCC-Order Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
@LocalTCC
@RequiredArgsConstructor
public class TccOrderService extends ServiceImpl<TccOrderMapper, TccOrder> {

    private final TccOrderMapper orderMapper;
    private final TccOrderTxMapper orderTxMapper;

    private final TccAccountFeignClient accountFeignClient;

    @TwoPhaseBusinessAction(name = "tryCreate", commitMethod = "confirm", rollbackMethod = "cancel")
    public void tryCreate(@BusinessActionContextParameter(paramName = "userId") String userId,
                          @BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
                          @BusinessActionContextParameter(paramName = "count") int count) {
        String xid = RootContext.getXID();
        log.info("create order in transaction: {}", xid);

        System.err.println("---------tryCreate-----------");

        // ====== 解决业务悬挂问题 ======
        TccOrderTx orderTx = orderTxMapper.selectOne(
                new LambdaQueryWrapper<TccOrderTx>()
                        .eq(TccOrderTx::getTxId, RootContext.getXID())
        );

        if (orderTx != null) {
            // 存在，说明已经cancel执行过，拒绝服务
            return;
        }

        // 订单总价 = 订购数量(count) * 商品单价(100)
        int orderMoney = count * 100;
        // 生成订单
        TccOrder order = TccOrder.builder()
                .userId(userId)
                .commodityCode(commodityCode)
                .count(count)
                .money(orderMoney)
                .build();
        orderMapper.insert(order);

        TccOrderTx tx = new TccOrderTx();
        tx.setTxId(RootContext.getXID());
        tx.setState(Constants.TxState.TRY.getState());
        orderTxMapper.insert(tx);

        // 调用账户余额扣减
        String result = accountFeignClient.reduce(userId, orderMoney);
        if (!Constants.SUCCESS.equals(result)) {
            throw new RuntimeException("调用账户服务失败");
        }
    }

    public boolean confirm(BusinessActionContext ctx) {
        System.err.println("---------confirm-----------");
        // 删除记录
        int ret = orderTxMapper.delete(
                new LambdaQueryWrapper<TccOrderTx>()
                        .eq(TccOrderTx::getTxId, ctx.getXid())
        );
        return ret == 1;
    }

    public boolean cancel(BusinessActionContext ctx) {
        System.err.println("------ cancel ------");

        String userId = Objects.requireNonNull(ctx.getActionContext("userId")).toString();
        String commodityCode = Objects.requireNonNull(ctx.getActionContext("commodityCode")).toString();
        int count = (int) Objects.requireNonNull(ctx.getActionContext("count"));

        TccOrderTx orderTx = orderTxMapper.selectOne(
                new LambdaQueryWrapper<TccOrderTx>().eq(TccOrderTx::getTxId, ctx.getXid()));
        // 空回滚
        if (orderTx == null) {
            orderTx = new TccOrderTx();
            orderTx.setTxId(ctx.getXid());
            orderTx.setState(Constants.TxState.CANCEL.getState());
            orderTxMapper.insert(orderTx);
            return true;
        }
        // 幂等处理
        if (orderTx.getState() == Constants.TxState.CANCEL.getState()) {
            return true;
        }

        // 恢复余额
        TccOrder tccOrder = orderMapper.selectOne(new LambdaQueryWrapper<TccOrder>()
                .eq(TccOrder::getUserId, userId)
                .eq(TccOrder::getCommodityCode, commodityCode)
                .eq(TccOrder::getCount, count)
                .orderByDesc(TccOrder::getId)
                .last("limit 1")
        );
        orderMapper.deleteById(tccOrder.getId());

        orderTx.setState(Constants.TxState.CANCEL.getState());
        int ret = orderTxMapper.updateById(orderTx);
        return ret == 1;
    }

}
