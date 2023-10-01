package cn.dlmyl.tcc.business.controller;

import cn.dlmyl.tcc.business.common.Constants;
import cn.dlmyl.tcc.business.service.TccBusinessService;
import cn.dlmyl.tcc.business.mock.TccMockData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TCC-Business Controller.
 *
 * @author dlmyL
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/businesses")
public class TccBusinessController {

    private final TccBusinessService businessService;

    @GetMapping(value = "/purchase")
    public String purchase(Boolean rollback, Integer count) {
        int orderCount = 10;
        if (count != null) {
            orderCount = count;
        }
        try {
            businessService.purchase(TccMockData.USER_ID, TccMockData.COMMODITY_CODE, orderCount,
                    rollback == null ? false : rollback.booleanValue());
        } catch (Exception ex) {
            log.error("Purchase Failed: {}", ex.getMessage(), ex);
            return "Purchase Failed: " + ex.getMessage();
        }
        return Constants.SUCCESS;
    }

}
