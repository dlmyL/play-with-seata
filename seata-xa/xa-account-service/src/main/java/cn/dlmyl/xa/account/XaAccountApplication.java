package cn.dlmyl.xa.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * XA-账户服务
 *
 * @author dlmyL
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class XaAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(XaAccountApplication.class, args);
    }

}
