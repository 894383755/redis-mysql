package com.kd.xxhyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@MapperScan("com.kd.xxhyf.mapper")
//@ComponentScan({"com.kd.xxhyf","com.kd.codis"})

//@ImportResource({"classpath:conf/spring-*.xml,"})
public class RedisMysqlApplication {
    public static void main(String[] args) {
            SpringApplication.run(RedisMysqlApplication.class, args);
    }
}
