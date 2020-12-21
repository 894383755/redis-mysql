package com.kd.xxhyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class RedisMysqlApplication {
    public static void main(String[] args) {
            SpringApplication.run(RedisMysqlApplication.class, args);
    }
}
