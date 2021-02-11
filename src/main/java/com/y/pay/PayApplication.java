package com.y.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackages = {"com.y.pay.connce.store"})
public class PayApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
