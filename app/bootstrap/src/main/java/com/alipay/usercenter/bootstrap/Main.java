package com.alipay.usercenter.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.alipay.usercenter")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}