package com.aaa.springboothomestay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.aaa.springboothomestay.dao")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@SpringBootApplication
public class SpringbootHomestayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHomestayApplication.class, args);
    }

}
