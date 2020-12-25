package com.kd.xxhyf.main.core;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.database.connection.Connection;
import com.kd.xxhyf.mysql_redis.Redis_Mysql;
import com.kd.xxhyf.notice.Notice;
import com.kd.xxhyf.static_model.Static_model;
import com.kd.xxhyf.synchro.Synchro;
import com.kd.xxhyf.synchro_data.SynchroData;
import redis.clients.jedis.JedisCommands;


/**
 * 
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:43:41
 *
 */

@Component
public class Run {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(Run.class);

	@Value("${config.rediskey}")
	private String REDISKEY;

	@Autowired
	private Static_model static_model;
	
	@Autowired
	private Redis_Mysql redis_Mysql;
	
	@Autowired
	private Connection connection;
	
	@Autowired
	private Synchro synchro;
	@Autowired
	private SynchroData synchroData;
	
	@Autowired
	private Notice notice;

	@Qualifier("getJedisCommands")
	@Autowired
	private JedisCommands jedis;

	/**
	 * 服务启停
	 */
	@PostConstruct
	public void start(){
		LOGGER.info("开始执行所有任务");
		init();
		redis_Mysql.run();//初始化静态信息同步到codis
		static_model.run();//静态模型入库
		synchro.run();//静态模型同步
		synchroData.run();
		notice.run();//待办任务
	}

	/**
	 * 初始化codis中存储的静态模型的最大ID,用于静态模型入库时获取ID使用，避免多线程导致ID重复问题
	 */
	public void init(){
		LOGGER.info("开始执行初始化");
		try  {
			String sql = "SELECT EN_TABLENAME,ID FROM OMPSE.SYS_TABLEINFO WHERE ID LIKE '%0' AND EN_TABLENAME!='RUNNING_FILE_B'";
			List<Map<String, Object>> list = connection.findForDruid(sql);
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i).get("ID")+"";
				String maxIdSql = "SELECT MAX(SUBSTR(ID,11,8)) AS NUM FROM OMPSE."+list.get(i).get("EN_TABLENAME");
				try {
					List<Map<String, Object>> list2 = connection.findForDruid(maxIdSql);
					if(list2.size()>0){
						Object object = list2.get(0).get("NUM");
						if(object==null||"".equals(object)){
							jedis.set(REDISKEY+id+"_num", "0");
						}else{
							jedis.set(REDISKEY+id+"_num", Integer.valueOf(String.valueOf(object))+"");
						}
					}else{
						jedis.set(REDISKEY+id+"_num", "0");
					}
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.error(maxIdSql);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("结束执行初始化");
	}
}
