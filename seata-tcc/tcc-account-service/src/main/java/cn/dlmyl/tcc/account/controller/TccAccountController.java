package cn.dlmyl.tcc.account.controller;

import cn.dlmyl.tcc.account.common.Constants;
import cn.dlmyl.tcc.account.service.TccAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户服务控制层
 *
 * @author dlmyL
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class TccAccountController {

    private final TccAccountService accountService;

    /**
     * 扣减账户余额
     *
     * @param userId 用户ID
     * @param money  要扣减的余额
     * @return 扣减结果，成功或者失败
     */
    @GetMapping(value = "/reduce")
    public String reduce(String userId, int money) {
        try {
            accountService.tryReduce(userId, money);
        } catch (Exception ex) {
            log.error("扣减账户余额失败: {}", ex.getMessage(), ex);
            return Constants.FAIL;
        }
        return Constants.SUCCESS;
    }

}