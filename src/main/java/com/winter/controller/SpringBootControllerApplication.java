package com.winter.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootControllerApplication.class, args);
    }

}
