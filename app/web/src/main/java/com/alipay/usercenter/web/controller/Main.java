package com.alipay.usercenter.web.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:spring/user-biz.xml")
@SpringBootApplication(scanBasePackages = "com.alipay.usercenter")
@MapperScan("com.alipay.usercenter.common.dal.auto.custom") // <- package of your DAOs
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}