package cn.dlmyl.xa.account.controller;

import cn.dlmyl.xa.account.common.Constants;
import cn.dlmyl.xa.account.service.AtAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AT-Account Controller.
 *
 * @author dlmyL
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AtAccountController {

    private final AtAccountService accountService;

    @GetMapping(value = "/reduce")
    public String reduce(String userId, int money) {
        try {
            accountService.reduce(userId, money);
        } catch (Exception ex) {
            log.error("扣减账户余额失败: {}", ex.getMessage(), ex);
            return Constants.FAIL;
        }
        return Constants.SUCCESS;
    }

}