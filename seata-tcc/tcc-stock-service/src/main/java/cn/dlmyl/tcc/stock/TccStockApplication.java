package cn.dlmyl.tcc.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * TCC-库存服务
 *
 * @author dlmyL
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class TccStockApplication {

    public static void main(String[] args) {
        log.info("====== TccStock Starting... ======");
        ConfigurableApplicationContext ctx = null;
        try {
            ctx = new SpringApplicationBuilder(TccStockApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .bannerMode(Banner.Mode.OFF)
                    .run(args);
            log.info("====== TccStock Started! ======");
        } catch (Exception e) {
            if (ctx != null) {
                log.error("TccStock application start failed and exit: {}", e.getMessage(), e);
                System.exit(SpringApplication.exit(ctx));
            }
        }
    }

}
