package cn.dlmyl.at.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * AT-库存服务
 *
 * @author dlmyL
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class AtStockApplication {

    public static void main(String[] args) {
        log.info("====== AtStock Starting... ======");
        ConfigurableApplicationContext ctx = null;
        try {
            ctx = new SpringApplicationBuilder(AtStockApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .bannerMode(Banner.Mode.OFF)
                    .run(args);
            log.info("====== AtStock Started! ======");
        } catch (Exception e) {
            if (ctx != null) {
                log.error("AtStock application start failed and exit: {}", e.getMessage(), e);
                System.exit(SpringApplication.exit(ctx));
            }
        }
    }

}
