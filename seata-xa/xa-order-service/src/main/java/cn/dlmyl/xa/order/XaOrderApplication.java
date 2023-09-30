package cn.dlmyl.xa.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * XA-订单服务
 *
 * @author dlmyL
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class XaOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XaOrderApplication.class, args);
    }

}