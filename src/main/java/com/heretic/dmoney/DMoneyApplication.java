package com.heretic.dmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class DMoneyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DMoneyApplication.class, args);
    }
}