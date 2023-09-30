package cn.dlmyl.xa.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用库存服务
 *
 * @author dlmyL
 */
@FeignClient(name = "stock-service")
public interface XaStockFeignClient {

    /**
     * 扣减库存
     *
     * @param commodityCode   要扣减的商品编号
     * @param count           要扣减的库存数量
     * @return {@link String} SUCCESS or FAIL
     */
    @GetMapping("/stocks/deduct")
    String deduct(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") int count);

}
