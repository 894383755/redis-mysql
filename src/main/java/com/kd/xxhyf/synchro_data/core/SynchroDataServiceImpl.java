package com.kd.xxhyf.synchro_data.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.database.connection.Connection;
import com.kd.xxhyf.mysql_redis.core.Redis_MysqlImpl;
import com.kd.xxhyf.resolveXml.resolveXml;

/**
 * 
 * 同步试图 静态 模型数据 和 数据库上数数据
 * 
 * @author 吴林豪 E-mail:807005278@163.com
 *
 * 2019年10月30日 下午2:28:36
 *
 */
@Component
public class SynchroDataServiceImpl implements Runnable{
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(SynchroDataServiceImpl.class);
	
	private RedisConfig redisConfig;
	
	private String receiveString;//接收的数据xml
	
	private Connection connection;//数据库连接池

	@Autowired
	private Redis_MysqlImpl redis_MysqlImpl;
	
	public SynchroDataServiceImpl(){
		
	}
	
	public SynchroDataServiceImpl(String receiveString,Connection connection, RedisConfig redisConfig){
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
			
			
			String tableOrSql = map.get("TABLEORSQL")+""; //获取到数据中表号
			redis_MysqlImpl.run(tableOrSql);
			//redis_MysqlImpl.runing_data(tableOrSql);
			redis_MysqlImpl.server();
			redis_MysqlImpl.syn_view();
			redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis();
			redis_MysqlImpl.synDeviceId();
	}
	
}
