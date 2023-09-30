package cn.dlmyl.xa.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Xa-Business Application.
 *
 * @author dlmyL
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class XaBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(XaBusinessApplication.class, args);
    }

}
