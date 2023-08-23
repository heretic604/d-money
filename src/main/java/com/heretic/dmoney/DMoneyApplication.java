package com.heretic.dmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories("com.heretic.dmoney.*")
//@ComponentScan(basePackages = { "com.heretic.dmoney.*" })
//@EntityScan("com.heretic.dmoney.entities")
@SpringBootApplication
public class DMoneyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DMoneyApplication.class, args);
    }
}