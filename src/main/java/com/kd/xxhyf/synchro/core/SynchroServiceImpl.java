package com.kd.xxhyf.synchro.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kd.xxhyf.entity.ompse.SysTableinfo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class SynchroServiceImpl{

	@Autowired
	private Redis_MysqlImpl redis_MysqlImpl;

	@Async
	public void run(String receiveString) {
			Map<String, Object> map = resolveXml.alarmXml(receiveString);//解析XML
			SysTableinfo sysTableinfo = new SysTableinfo();
			sysTableinfo.setId(map.get("TABLEORSQL")+"");//获取到数据中表号
			redis_MysqlImpl.run(sysTableinfo);
			redis_MysqlImpl.runing_data();
			//redis_MysqlImpl.server();
			redis_MysqlImpl.syn_view();
			redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis();
			redis_MysqlImpl.synDeviceId();
	}
	
}
