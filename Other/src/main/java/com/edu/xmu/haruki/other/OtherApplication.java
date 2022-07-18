package com.edu.xmu.haruki.other;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.edu.xmu.haruki.other.mapper")
public class OtherApplication {
    public static void main(String[] args) {
        SpringApplication.run(OtherApplication.class,args);
    }
}
