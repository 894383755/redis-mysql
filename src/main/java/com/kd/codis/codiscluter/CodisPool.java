/*package com.kd.codis.codiscluter;

import com.google.common.base.Strings;
import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import io.codis.jodis.RoundRobinJedisPool.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;

*//**
 * 配置codis连接池
 * Created by byddong on 2017/12/14.
 * fix by wulinhao 2019/10/15
 *//*
@Component
public class CodisPool
{
    private static final Logger LOGGER =  LoggerFactory.getLogger(CodisPool.class);
    
    public JedisResourcePool jedisPool;

    private static JedisPoolConfig jedisPoolConfig;
    
    private String zkAddr;
    
    private String Auth;
    
    private String zkDir;
    
    private int maxAttempts;
    
    private String aloneHost;
    
    private int alonePort;
    
    private String redisType;
    
    public JedisResourcePool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisResourcePool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public String getZkAddr() {
		return zkAddr;
	}

	public void setZkAddr(String zkAddr) {
		this.zkAddr = zkAddr;
	}
	
	public String getAuth() {
		return Auth;
	}

	public void setAuth(String auth) {
		Auth = auth;
	}

	public String getZkDir() {
		return zkDir;
	}

	public void setZkDir(String zkDir) {
		this.zkDir = zkDir;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
	
	public String getAloneHost() {
		return aloneHost;
	}

	public void setAloneHost(String aloneHost) {
		this.aloneHost = aloneHost;
	}

	public int getAlonePort() {
		return alonePort;
	}

	public void setAlonePort(int alonePort) {
		this.alonePort = alonePort;
	}

	public String getRedisType() {
		return redisType;
	}

	public void setRedisType(String redisType) {
		this.redisType = redisType;
	}

	public void build(){
        LOGGER.info("zkAddr:" + zkAddr + " | pwd:" + Auth + " | zkdir:" + zkDir);
        if (jedisPool == null){
        	if(Strings.isNullOrEmpty(Auth)){
				jedisPool = RoundRobinJedisPool.create().curatorClient(zkAddr, 30000).poolConfig(jedisPoolConfig).zkProxyDir(zkDir).build();
			}else{
				jedisPool = RoundRobinJedisPool.create().curatorClient(zkAddr, 30000).poolConfig(jedisPoolConfig).password(Auth).zkProxyDir(zkDir).build();
			}
            System.out.println("jedisPool is build");
        }
    }
}
*/