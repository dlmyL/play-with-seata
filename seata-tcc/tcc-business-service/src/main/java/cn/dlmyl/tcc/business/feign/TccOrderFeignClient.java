package cn.dlmyl.tcc.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用订单服务
 *
 * @author dlmyL
 */
@FeignClient(name = "tcc-order-service")
public interface TccOrderFeignClient {

    /**
     * 创建订单
     *
     * @param userId          用户ID
     * @param commodityCode   商品编号
     * @param orderCount      订单数量
     * @return {@link String} SUCCESS or FAIL
     */
    @GetMapping("/orders/create")
    String create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode,
                  @RequestParam("orderCount") int orderCount);

}