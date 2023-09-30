package cn.dlmyl.xa.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * XA-库存服务
 *
 * @author dlmyL
 */
@EnableDiscoveryClient
@SpringBootApplication
public class XaStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(XaStockApplication.class, args);
    }

}
