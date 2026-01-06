package com.carbon.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Carbon Auth 应用启动类
 */
@SpringBootApplication
@EnableFeignClients  // 启用 Feign 客户端，用于调用系统服务
@MapperScan("com.carbon.auth.mapper")
public class CarbonAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonAuthApplication.class, args);
        System.out.println("=================================");
        System.out.println("Carbon Auth Service Started Successfully!");
        System.out.println("=================================");
    }

}

