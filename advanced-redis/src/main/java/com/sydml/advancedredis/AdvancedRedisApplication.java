package com.sydml.advancedredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sydml.advancedredis")
public class AdvancedRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedRedisApplication.class, args);
    }

}
