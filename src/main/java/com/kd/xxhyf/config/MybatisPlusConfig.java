package com.kd.xxhyf.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.kd.xxhyf.Interceptor.MyDynamicTableNameInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@MapperScan("com.kd.xxhyf.mapper")
public class MybatisPlusConfig {

    @Autowired
    private MyDynamicTableNameInnerInterceptor myDynamicTableNameInnerInterceptor;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(myDynamicTableNameInnerInterceptor);
        return interceptor;
    }

}