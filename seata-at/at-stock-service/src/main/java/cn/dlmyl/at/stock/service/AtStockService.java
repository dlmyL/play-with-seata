package cn.dlmyl.at.stock.service;

import cn.dlmyl.at.stock.mapper.AtStockMapper;
import cn.dlmyl.at.stock.model.AtStock;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AT-Stock Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
public class AtStockService extends ServiceImpl<AtStockMapper, AtStock> {

    /**
     * 扣减库存
     *
     * @param commodityCode 商品编码
     * @param count         扣减数量
     */
    public void deduct(String commodityCode, int count) {
        String xid = RootContext.getXID();
        log.info("deduct stock balance in transaction: " + xid);

        AtStock stock = lambdaQuery().eq(AtStock::getCommodityCode, commodityCode).one();
        if (stock != null && stock.getCount() < count) {
            throw new RuntimeException("库存不够了！");
        }

        lambdaUpdate().setSql("count = count-" + count)
                .eq(AtStock::getCommodityCode, commodityCode)
                .update();
    }

}
