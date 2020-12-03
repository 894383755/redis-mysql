package com.kd.xxhyf.mysql_redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kd.xxhyf.main.core.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.database.connection.Connection;
import com.kd.xxhyf.mysql_redis.core.Redis_MysqlImpl;

/**
 * 处理redis-mysql数据同步
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年10月9日 上午10:19:22
 *
 */
@Component
public class Redis_Mysql {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Redis_Mysql.class);
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor1;
	
	@Autowired
	private RedisConfig redisConfig;
	
	@Autowired
	private Connection connection;
	
	@Async
	@Scheduled(fixedDelay = 20000)
	public void start(){
		//是否准备好了，准备好了就启动
		while(Run.getReady()){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LOGGER.debug("开始同步");
	//	List<Map<String, Object>> list = connection.findForDruid("SELECT * FROM OMPSE.SYS_TABLEINFO WHERE ID LIKE '1%' "
	//			+ "UNION ALL SELECT * FROM OMPSE.SYS_TABLEINFO  WHERE ID LIKE '2%'; ");
		List<Map<String, Object>> list = connection.findForDruid("SELECT * FROM OMPSE.SYS_TABLEINFO");
		for (int i = 0; i < list.size(); i++) {
			taskExecutor1.execute(new Redis_MysqlImpl(redisConfig, list.get(i),connection));
		}
		LOGGER.debug("开始同步告警配置信息");
		Redis_MysqlImpl redis_MysqlImpl = new Redis_MysqlImpl(redisConfig, new HashMap<String, Object>(), connection);
		redis_MysqlImpl.runing_data("null");
		redis_MysqlImpl.server();
		redis_MysqlImpl.syn_view();
		redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis();
		redis_MysqlImpl.synDeviceId();
	}

}
