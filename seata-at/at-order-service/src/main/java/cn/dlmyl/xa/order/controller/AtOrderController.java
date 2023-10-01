package cn.dlmyl.xa.order.controller;

import cn.dlmyl.xa.order.common.Constants;
import cn.dlmyl.xa.order.service.AtOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AT-Order Controller.
 *
 * @author dlmyL
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class AtOrderController {

    private final AtOrderService orderService;

    @GetMapping(value = "/create")
    public String create(String userId, String commodityCode, int orderCount) {
        try {
            orderService.create(userId, commodityCode, orderCount);
        } catch (Exception ex) {
            log.error("创建订单失败: {}", ex.getMessage(), ex);
            return Constants.FAIL;
        }
        return Constants.SUCCESS;
    }

}
