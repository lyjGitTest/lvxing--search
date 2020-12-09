package com.boot.itripbootmybatisscreah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mapper","com.serivce","com.controller","com.pojo"})
public class ItripBootmybatisScreahApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItripBootmybatisScreahApplication.class, args);
    }

}
