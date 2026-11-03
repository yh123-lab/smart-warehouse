package com.smartwarehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 智慧仓储管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.smartwarehouse.mapper")
@EnableScheduling
public class SmartWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartWarehouseApplication.class, args);
        System.out.println("====================================");
        System.out.println("  智慧仓储管理系统启动成功");
        System.out.println("  http://localhost:8080");
        System.out.println("====================================");
    }
}
