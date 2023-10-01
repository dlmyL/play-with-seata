package cn.dlmyl.at.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * AT-账户服务
 *
 * @author dlmyL
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class AtAccountApplication {

    public static void main(String[] args) {
        log.info("====== AtAccount Starting... ======");
        ConfigurableApplicationContext ctx = null;
        try {
            ctx = new SpringApplicationBuilder(AtAccountApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .bannerMode(Banner.Mode.OFF)
                    .run(args);
            log.info("====== AtAccount Started! ======");
        } catch (Exception e) {
            if (ctx != null) {
                log.error("AtAccount application start failed and exit: {}", e.getMessage(), e);
                System.exit(SpringApplication.exit(ctx));
            }
        }
    }

}
