package cn.dlmyl.xa.business.controller;

import cn.dlmyl.xa.business.common.Constants;
import cn.dlmyl.xa.business.mock.AtMockData;
import cn.dlmyl.xa.business.service.AtBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AT-Business Controller.
 *
 * @author dlmyL
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/businesses")
public class AtBusinessController {

    private final AtBusinessService businessService;

    @GetMapping(value = "/purchase")
    public String purchase(Boolean rollback, Integer count) {
        int orderCount = 10;
        if (count != null) {
            orderCount = count;
        }
        try {
            businessService.purchase(AtMockData.USER_ID, AtMockData.COMMODITY_CODE, orderCount,
                    rollback == null ? false : rollback.booleanValue());
        } catch (Exception ex) {
            log.error("Purchase Failed: {}", ex.getMessage(), ex);
            return "Purchase Failed: " + ex.getMessage();
        }
        return Constants.SUCCESS;
    }

}
