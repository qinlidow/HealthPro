package com.learning.healthpro;

import com.learning.healthpro.utils.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.learning.healthpro.mapper")
@SpringBootApplication
public class HealthProApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthProApplication.class, args);
    }



}
