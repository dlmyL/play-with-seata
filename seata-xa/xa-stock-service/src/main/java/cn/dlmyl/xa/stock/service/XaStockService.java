package cn.dlmyl.xa.stock.service;

import cn.dlmyl.xa.stock.mapper.XaStockMapper;
import cn.dlmyl.xa.stock.model.XaStock;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * XA-Stock Service.
 *
 * @author dlmyL
 */
@Slf4j
@Service
public class XaStockService extends ServiceImpl<XaStockMapper, XaStock> {

    /**
     * 扣减库存
     *
     * @param commodityCode 商品编码
     * @param count         扣减数量
     */
    public void deduct(String commodityCode, int count) {
        String xid = RootContext.getXID();
        log.info("deduct stock balance in transaction: " + xid);

        XaStock stock = lambdaQuery().eq(XaStock::getCommodityCode, commodityCode).one();
        if (stock != null && stock.getCount() < count) {
            throw new RuntimeException("库存不够了！");
        }
        lambdaUpdate().setSql("count = count-" + count)
                .eq(XaStock::getCommodityCode, commodityCode)
                .update();
    }

}
