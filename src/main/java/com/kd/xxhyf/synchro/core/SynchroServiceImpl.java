package com.kd.xxhyf.synchro.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.database.connection.Connection;
import com.kd.xxhyf.mysql_redis.core.Redis_MysqlImpl;
import com.kd.xxhyf.resolveXml.resolveXml;

/**
 *    数据库 同步到 实时库
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:43:10
 *
 */
@Component
public class SynchroServiceImpl implements Runnable{
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(SynchroServiceImpl.class);
	

	private RedisConfig redisConfig;
	
	private String receiveString;//接收的数据xml
	
	private Connection connection;//数据库连接池
	
	public SynchroServiceImpl(){
		
	}
	
	public SynchroServiceImpl(String receiveString,Connection connection, RedisConfig redisConfig){
		this.receiveString = receiveString;
		this.redisConfig = redisConfig;
		this.connection = connection;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			try {		
				map = resolveXml.alarmXml(receiveString);//解析XML
				//System.out.println(receiveString);
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(receiveString+"---"+e.getMessage());
				//System.out.println(receiveString);
			}
			
			Set<String> ids = map.keySet();//遍历Map
			
			String tableOrSql = map.get("TABLEORSQL")+""; //获取到数据中表号
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("ID", tableOrSql);
			Redis_MysqlImpl redis_MysqlImpl = new  Redis_MysqlImpl(redisConfig, map2, connection);
			redis_MysqlImpl.run();
			redis_MysqlImpl.runing_data("null");
			//redis_MysqlImpl.server();
			redis_MysqlImpl.syn_view();
			redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis();
			redis_MysqlImpl.synDeviceId();
	}
	
}
