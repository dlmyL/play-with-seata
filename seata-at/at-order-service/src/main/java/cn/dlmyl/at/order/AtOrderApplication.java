package cn.dlmyl.at.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * AT-订单服务
 *
 * @author dlmyL
 */
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AtOrderApplication {

    public static void main(String[] args) {
        log.info("====== AtOrder Starting... ======");
        ConfigurableApplicationContext ctx = null;
        try {
            ctx = new SpringApplicationBuilder(AtOrderApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .bannerMode(Banner.Mode.OFF)
                    .run(args);
            log.info("====== AtOrder Started! ======");
        } catch (Exception e) {
            if (ctx != null) {
                log.error("AtOrder application start failed and exit: {}", e.getMessage(), e);
                System.exit(SpringApplication.exit(ctx));
            }
        }
    }

}