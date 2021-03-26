package com.kd.xxhyf.synchro_data.core;

import java.util.Map;

import com.kd.xxhyf.entity.ompse.SysTableinfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.util.Connection;
import com.kd.xxhyf.mysql_redis.core.RedisToMysqlImpl;
import com.kd.xxhyf.util.XmlUtil;

/**
 * 
 * 同步试图 静态 模型数据 和 数据库上数数据
 * 
 * @author 吴林豪 E-mail:807005278@163.com
 *
 * 2019年10月30日 下午2:28:36
 *
 */
@Slf4j
@Component
public class SynchroDataServiceImpl {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(SynchroDataServiceImpl.class);

	@Autowired
	private RedisConfig redisConfig;
	@Autowired
	private Connection connection;

	@Autowired
	private RedisToMysqlImpl redis_To_MysqlImpl;

	@Async
	public void run(String receiveString) {
			Map<String, Object> map = XmlUtil.alarmXml(receiveString);//解析XML
			SysTableinfo sysTableinfo = new SysTableinfo();
			sysTableinfo.setId(map.get("TABLEORSQL")+"");//获取到数据中表号
			redis_To_MysqlImpl.run(sysTableinfo);
			//redis_MysqlImpl.runing_data(tableOrSql);
			redis_To_MysqlImpl.server();
			redis_To_MysqlImpl.syn_view();
			redis_To_MysqlImpl.synNowRunDateNeedStaticDataToCodis();
			redis_To_MysqlImpl.synDeviceId();
	}
	
}
