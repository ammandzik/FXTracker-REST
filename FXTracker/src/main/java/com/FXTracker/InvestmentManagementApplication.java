package com.FXTracker;

import com.FXTracker.env.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvestmentManagementApplication {
    public static void main(String[] args) {

        System.setProperty("DB_USER", EnvLoader.get("DB_USER"));
        System.setProperty("DB_PASS", EnvLoader.get("DB_PASS"));
        System.setProperty("DB_HOST", EnvLoader.get("DB_HOST"));
        System.setProperty("DB_PORT", EnvLoader.get("DB_PORT"));
        System.setProperty("DB_NAME", EnvLoader.get("DB_NAME"));

        SpringApplication.run(InvestmentManagementApplication.class, args);

    }
}
