package cn.dlmyl.tcc.stock.controller;

import cn.dlmyl.tcc.stock.common.Constants;
import cn.dlmyl.tcc.stock.service.TccStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TCC-Stock Controller.
 *
 * @author dlmyL
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class TccStockController {

    private final TccStockService stockService;

    @GetMapping(value = "/deduct")
    public String deduct(String commodityCode, int count) {
        try {
            stockService.tryDeduct(commodityCode, count);
        } catch (Exception ex) {
            log.error("扣减库存失败：{}", ex.getMessage(), ex);
            return Constants.FAIL;
        }
        return Constants.SUCCESS;
    }

}
