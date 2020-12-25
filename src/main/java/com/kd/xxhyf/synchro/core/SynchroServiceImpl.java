package com.kd.xxhyf.synchro.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kd.xxhyf.mysql_redis.core.Redis_MysqlImpl;
import com.kd.xxhyf.util.resolveXml;

/**
 *    数据库 同步到 实时库
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:43:10
 *
 */
@Component
public class SynchroServiceImpl{
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(SynchroServiceImpl.class);

	@Autowired
	private Redis_MysqlImpl redis_MysqlImpl;

	@Async
	public void run(String receiveString) {
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

			redis_MysqlImpl.run(tableOrSql);
			redis_MysqlImpl.runing_data("null");
			//redis_MysqlImpl.server();
			redis_MysqlImpl.syn_view();
			redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis();
			redis_MysqlImpl.synDeviceId();
	}
	
}
