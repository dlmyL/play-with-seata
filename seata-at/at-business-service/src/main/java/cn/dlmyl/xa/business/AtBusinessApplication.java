package cn.dlmyl.xa.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * AT-Business Application.
 *
 * @author dlmyL
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AtBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtBusinessApplication.class, args);
    }

}
