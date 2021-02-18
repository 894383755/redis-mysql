package com.kd.xxhyf.config;

import com.kd.redis.config.RedisConfig;
import com.kd.redis.config.RedisProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jboss.netty.util.internal.ReusableIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;


@Configuration
@Data
@Slf4j
public class MyRedisConfig {

    @Value("${redis.nodes}")
    private String nodes;
    @Value("${redis.auth}")
    private String auth;
    @Value("${redis.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.maxIdle}")
    private Integer maxIdle;
    @Value("${redis.minIdle}")
    private Integer minIdle;
    @Value("${redis.connectionTimeout}")
    private Integer connectionTimeout;
    @Value("${redis.maxAttempts}")
    private Integer maxAttempts;
    @Value("${redis.soTimeout}")
    private Integer soTimeout;
    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Bean
    public RedisConfig getRedisConfig(){
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setNodes(nodes);
        redisProperties.setAuth(auth);
        redisProperties.setMaxTotal(maxTotal);
        redisProperties.setMaxIdle(maxIdle);
        redisProperties.setMinIdle(minIdle);
        redisProperties.setConnectionTimeout(connectionTimeout);
        redisProperties.setMaxAttempts(maxAttempts);
        redisProperties.setSoTimeout(soTimeout);
        redisProperties.setMaxWaitMillis(maxWaitMillis);
        return new RedisConfig(redisProperties);
    }

    //@Bean
    public JedisCluster getJedisCluster(RedisConfig redisConfig){
        try {
            return redisConfig.getJedisCluster();
        }catch (Exception e){
            log.warn("获取redis集群连接错误",e);
            return null;
        }
    }

    //@Bean
    public Jedis getJedis(RedisConfig redisConfig){
        try {
            return redisConfig.getJedis();
        }catch (Exception e){
            log.warn("获取redis连接错误",e);
            return null;
        }
    }

    @Bean
    public JedisCommands getJedisCommands(RedisConfig redisConfig){
        try {
            return redisConfig.getJedis();
        }catch (Exception e){
            //log.warn("获取redis连接错误",e);
        }
        try {
            return redisConfig.getJedisCluster();
        }catch (Exception e){
//            log.warn("获取redis集群连接错误",e);
        }
        log.warn("无法获取redis或者jedisCluster连接");
        //return null;
        throw new RuntimeException("无法获取redis或者jedisCluster连接");
    }

}
