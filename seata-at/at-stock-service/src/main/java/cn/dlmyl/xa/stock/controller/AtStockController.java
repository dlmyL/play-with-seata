package cn.dlmyl.xa.stock.controller;

import cn.dlmyl.xa.stock.common.Constants;
import cn.dlmyl.xa.stock.service.AtStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AT-Stock Controller.
 *
 * @author dlmyL
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class AtStockController {

    private final AtStockService stockService;

    @GetMapping(value = "/deduct")
    public String deduct(String commodityCode, int count) {
        try {
            stockService.deduct(commodityCode, count);
        } catch (Exception ex) {
            log.error("扣减库存失败：{}", ex.getMessage(), ex);
            return Constants.FAIL;
        }
        return Constants.SUCCESS;
    }

}
