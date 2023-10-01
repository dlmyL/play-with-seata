package cn.dlmyl.at.business;

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
 * AT-Business Application.
 *
 * @author dlmyL
 */
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AtBusinessApplication {

    public static void main(String[] args) {
        log.info("====== AtBusiness Starting... ======");
        ConfigurableApplicationContext ctx = null;
        try {
            ctx = new SpringApplicationBuilder(AtBusinessApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .bannerMode(Banner.Mode.OFF)
                    .run(args);
            log.info("====== AtBusiness Started! ======");
        } catch (Exception e) {
            if (ctx != null) {
                log.error("AtBusiness application start failed and exit: {}", e.getMessage(), e);
                System.exit(SpringApplication.exit(ctx));
            }
        }
    }

}
