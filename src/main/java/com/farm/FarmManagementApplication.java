package com.farm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.farm.repository")
@EntityScan(basePackages = "com.farm.model")
@EnableScheduling
public class FarmManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(FarmManagementApplication.class, args);
    }
}
