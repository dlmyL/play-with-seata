package cn.dlmyl.tcc.stock.service;

import cn.dlmyl.tcc.stock.common.Constants;
import cn.dlmyl.tcc.stock.mapper.TccStockMapper;
import cn.dlmyl.tcc.stock.mapper.TccStockTxMapper;
import cn.dlmyl.tcc.stock.model.TccStock;
import cn.dlmyl.tcc.stock.model.TccStockTx;
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
 * TCC-Stock Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
@LocalTCC
@RequiredArgsConstructor
public class TccStockService extends ServiceImpl<TccStockMapper, TccStock> {

    private final TccStockMapper stockMapper;
    private final TccStockTxMapper stockTxMapper;

    /**
     * 扣减库存
     *
     * @param commodityCode 商品编码
     * @param count         扣减数量
     */
    @TwoPhaseBusinessAction(name = "tryDeduct", commitMethod = "confirm", rollbackMethod = "cancel")
    public void tryDeduct(@BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
                          @BusinessActionContextParameter(paramName = "count") int count) {

        String xid = RootContext.getXID();
        log.info("deduct stock balance in transaction: " + xid);

        System.err.println("------ tryDeduct ------");


        // ====== 业务悬挂 ======
        TccStockTx stockTx = stockTxMapper.selectOne(
                new LambdaQueryWrapper<TccStockTx>().eq(TccStockTx::getTxId, RootContext.getXID())
        );
        if (stockTx != null) {
            // 存在，说明已经cancel已执行过，直接返回
            return;
        }


        TccStock stock = stockMapper.selectOne(
                new LambdaQueryWrapper<TccStock>().eq(TccStock::getCommodityCode, commodityCode)
        );
        if (stock != null && stock.getCount() < count) {
            throw new RuntimeException("库存不够了！");
        }

        stockMapper.update(null, new LambdaUpdateWrapper<TccStock>()
                .setSql("count = count-" + count).eq(TccStock::getCommodityCode, commodityCode));


        TccStockTx tx = new TccStockTx();
        tx.setCount(count);
        tx.setTxId(RootContext.getXID());
        tx.setState(Constants.TxState.TRY.getState());
        stockTxMapper.insert(tx);
    }


    public boolean confirm(BusinessActionContext ctx) {
        System.err.println("---------confirm-----------");
        // 删除记录
        int ret = stockTxMapper.delete(
                new LambdaQueryWrapper<TccStockTx>().eq(TccStockTx::getTxId, ctx.getXid())
        );
        return ret == 1;
    }


    public boolean cancel(BusinessActionContext ctx) {

        System.err.println("------ cancel ------");

        String count = Objects.requireNonNull(ctx.getActionContext("count")).toString();
        String commodityCode = Objects.requireNonNull(ctx.getActionContext("commodityCode")).toString();

        TccStockTx stockTx = stockTxMapper.selectOne(
                new LambdaQueryWrapper<TccStockTx>().eq(TccStockTx::getTxId, ctx.getXid()));

        if (stockTx == null) {
            // 为空，空回滚
            stockTx = new TccStockTx();
            stockTx.setTxId(ctx.getXid());
            stockTx.setState(Constants.TxState.CANCEL.getState());
            if (count != null) {
                stockTx.setCount(Integer.parseInt(count));
            }
            stockTxMapper.insert(stockTx);
            return true;
        }

        // 幂等处理
        if (stockTx.getState() == Constants.TxState.CANCEL.getState()) {
            return true;
        }

        // 恢复余额
        stockMapper.update(null, new LambdaUpdateWrapper<TccStock>()
                .setSql("count = count + " + count).eq(TccStock::getCommodityCode, commodityCode));

        stockTx.setCount(0);
        stockTx.setState(Constants.TxState.CANCEL.getState());
        int ret = stockTxMapper.updateById(stockTx);
        return ret == 1;
    }

}
