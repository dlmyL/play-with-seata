package cn.dlmyl.tcc.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * TCC-库存服务
 *
 * @author dlmyL
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TccStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccStockApplication.class, args);
    }

}
