package cn.dlmyl.tcc.stock.service;

import cn.dlmyl.tcc.stock.model.TccStock;
import cn.dlmyl.tcc.stock.mapper.TccStockMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TCC-Stock Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
public class TccStockService extends ServiceImpl<TccStockMapper, TccStock> {

    /**
     * 扣减库存
     *
     * @param commodityCode 商品编码
     * @param count         扣减数量
     */
    public void deduct(String commodityCode, int count) {
        String xid = RootContext.getXID();
        log.info("deduct stock balance in transaction: " + xid);

        TccStock stock = lambdaQuery().eq(TccStock::getCommodityCode, commodityCode).one();
        if (stock != null && stock.getCount() < count) {
            throw new RuntimeException("库存不够了！");
        }
        lambdaUpdate().setSql("count = count-" + count)
                .eq(TccStock::getCommodityCode, commodityCode)
                .update();
    }

}
