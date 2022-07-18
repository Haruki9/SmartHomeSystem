package com.edu.xmu.haruki.DashboardProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author haruki_9
 * @date 2022/7/17
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class DashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class);
    }
}
