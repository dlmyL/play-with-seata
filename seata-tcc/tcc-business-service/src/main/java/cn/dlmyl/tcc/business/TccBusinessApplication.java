package cn.dlmyl.tcc.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * TCC-Business Application.
 *
 * @author dlmyL
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class TccBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccBusinessApplication.class, args);
    }

}
