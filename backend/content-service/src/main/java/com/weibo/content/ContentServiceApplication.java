package com.weibo.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = "com.weibo")
public class ContentServiceApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(ContentServiceApplication.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}