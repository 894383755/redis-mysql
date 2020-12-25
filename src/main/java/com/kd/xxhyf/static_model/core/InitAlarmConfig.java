package com.kd.xxhyf.static_model.core;

import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.bean.Connection;

public class InitAlarmConfig implements Runnable {

	private String device_id;
	
	private String type;
	
	private Connection connection;
	
	private RedisConfig redisConfig;

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}




	

	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	public void setRedisConfig(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}

	public InitAlarmConfig(String device_id, String type,Connection connection, RedisConfig redisConfig) {
		super();
		this.device_id = device_id;
		this.type = type;
		this.connection = connection;
		this.redisConfig = redisConfig;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
			System.err.println(type);
			System.err.println(device_id);
			String sql = "";
			
	}

}
