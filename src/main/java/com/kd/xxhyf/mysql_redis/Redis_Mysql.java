package com.kd.xxhyf.mysql_redis;

import java.util.List;
import java.util.Map;

import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.mapper.SysTableInfoMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kd.xxhyf.util.Connection;
import com.kd.xxhyf.mysql_redis.core.Redis_MysqlImpl;

import javax.annotation.Resource;

/**
 * 处理redis-mysql数据同步
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年10月9日 上午10:19:22
 *
 */
@Component
@Data
public class Redis_Mysql {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Redis_Mysql.class);

	@Resource
	private SysTableInfoMapper sysTableInfoMapper;

	@Autowired
	private Connection connection;

	@Autowired
	Redis_MysqlImpl redis_MysqlImpl;

	@Scheduled(fixedDelay = 20000)
	public void run(){
		LOGGER.info("开始同步任务");
		List<SysTableinfo> sysTableinfos = sysTableInfoMapper.selectList(null);
		for (SysTableinfo sysTableinfo : sysTableinfos) {
			redis_MysqlImpl.run(sysTableinfo.toString());
		}
		LOGGER.debug("开始同步告警配置信息");
		redis_MysqlImpl.runing_data();
		redis_MysqlImpl.server();//t同步服务器数据
		redis_MysqlImpl.syn_view();//同步视图
		redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis();
		redis_MysqlImpl.synDeviceId();
		LOGGER.info("结束同步任务");
	}

}
