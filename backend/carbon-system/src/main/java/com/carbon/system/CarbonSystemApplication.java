package com.carbon.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Carbon System 服务启动类
 */
@SpringBootApplication
@MapperScan("com.carbon.system.mapper")
public class CarbonSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonSystemApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Carbon System Service Started Successfully!");
        System.out.println("========================================\n");
    }
}

