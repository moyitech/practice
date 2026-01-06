package com.carbon.assets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Carbon Assets 服务启动类
 */
@SpringBootApplication
@MapperScan("com.carbon.assets.mapper")
@EnableFeignClients(basePackages = "com.carbon.assets.service.feign")
public class CarbonAssetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonAssetsApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Carbon Assets Service Started Successfully!");
        System.out.println("========================================\n");
    }
}

