package cn.dlmyl.tcc.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * TCC-订单服务
 *
 * @author dlmyL
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class TccOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccOrderApplication.class, args);
    }

}