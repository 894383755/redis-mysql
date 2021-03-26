package com.kd.xxhyf.synchro.core;

import java.util.Map;

import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.mysql_redis.core.RedisToMysql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kd.xxhyf.mysql_redis.core.RedisToMysqlImpl;
import com.kd.xxhyf.util.XmlUtil;

/**
 *    数据库 同步到 实时库
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:43:10
 *
 */
@Slf4j
@Component
public class SynchroServiceImpl implements SynchroService {

	@Autowired
	private RedisToMysql redisToMysql;

	@Async
	@Override
	public void run(String receiveString) {
		Map<String, Object> map = XmlUtil.alarmXml(receiveString);//解析XML
		SysTableinfo sysTableinfo = new SysTableinfo();
		sysTableinfo.setId(map.get("TABLEORSQL")+"");//获取到数据中表号
		redisToMysql.run(sysTableinfo);
		redisToMysql.runing_data();
			//redis_MysqlImpl.server();
		redisToMysql.syn_view();
		redisToMysql.synNowRunDateNeedStaticDataToCodis();
		redisToMysql.synDeviceId();
	}
	
}
