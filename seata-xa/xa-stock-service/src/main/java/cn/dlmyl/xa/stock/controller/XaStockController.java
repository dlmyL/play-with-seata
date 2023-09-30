package cn.dlmyl.xa.stock.controller;

import cn.dlmyl.xa.stock.common.Constants;
import cn.dlmyl.xa.stock.service.XaStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * XA-Stock Controller.
 *
 * @author dlmyL
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class XaStockController {

    private final XaStockService stockService;

    @GetMapping(value = "/deduct")
    public String deduct(String commodityCode, int count) {
        try {
            stockService.deduct(commodityCode, count);
        } catch (Exception exx) {
            exx.printStackTrace();
            return Constants.FAIL;
        }
        return Constants.SUCCESS;
    }

}
