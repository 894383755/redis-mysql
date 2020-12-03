package com.kd.codis.codiscluter;


import com.kd.redis.config.RedisConfig;

public class JedisClusterPoolConfig {
	
	private RedisConfig redisConfig;
	   
	   

	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	public void setRedisConfig(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}
	
	
	   /* @Value("${zkAddr}")
	    private String nodes;
	    @Value("${auth}")
	    private String auth;
	    @Value("${redis.maxTotal}")
	    private Integer maxTotal;
	    @Value("${redis.maxIdle}")
	    private Integer maxIdle;
	    @Value("${redis.minIdle}")
	    private Integer minIdle;
	    @Value("${redis.maxWaitMillis}")
	    private Integer maxWaitMillis;
	    @Value("${redis.connectionTimeout}")
	    private Integer connectionTimeout;
	    @Value("${redis.soTimeout}")
	    private Integer soTimeout;
	    @Value("${redis.maxAttempts}")
	    private Integer maxAttempts;

	    @Bean
	    public RedisProperties getRedisProperties(){
	        RedisProperties redisProperties = new RedisProperties();
	        redisProperties.setNodes(nodes);
	        redisProperties.setAuth(auth);
	        redisProperties.setMaxTotal(maxTotal);
	        redisProperties.setMaxIdle(maxIdle);
	        redisProperties.setMinIdle(minIdle);
	        redisProperties.setMaxWaitMillis(maxWaitMillis);
	        redisProperties.setConnectionTimeout(connectionTimeout);
	        redisProperties.setMaxAttempts(maxAttempts);
	        redisProperties.setSoTimeout(soTimeout);

	        //redisProperties.set
	        return  redisProperties;

	    }

	    @Bean
	    public RedisConfig getJedis(@Qualifier("getRedisProperties") RedisProperties getRedisProperties){

	        return   new RedisConfig(getRedisProperties);
	    }*/
	
	
     
     

     
     
}
