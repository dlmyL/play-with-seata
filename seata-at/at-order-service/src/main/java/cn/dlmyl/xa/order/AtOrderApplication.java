package cn.dlmyl.xa.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * AT-订单服务
 *
 * @author dlmyL
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AtOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtOrderApplication.class, args);
    }

}