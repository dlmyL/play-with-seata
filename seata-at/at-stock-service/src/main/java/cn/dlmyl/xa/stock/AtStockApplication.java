package cn.dlmyl.xa.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AT-库存服务
 *
 * @author dlmyL
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AtStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtStockApplication.class, args);
    }

}
