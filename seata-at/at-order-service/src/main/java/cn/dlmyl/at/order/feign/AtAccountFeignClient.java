package cn.dlmyl.at.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用账户服务
 *
 * @author dlmyL
 */
@FeignClient(name = "at-account-service")
public interface AtAccountFeignClient {

    /**
     * 账户余额扣减
     *
     * @param userId          用户ID
     * @param money           要扣减的金额
     * @return {@link String} SUCCESS or FAIL
     */
    @GetMapping("/accounts/reduce")
    String reduce(@RequestParam("userId") String userId, @RequestParam("money") int money);

}
