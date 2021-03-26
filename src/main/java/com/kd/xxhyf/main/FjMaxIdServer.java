package com.kd.xxhyf.main;


import com.kd.xxhyf.notice.core.Notice;
import com.kd.xxhyf.synchro.Synchro;
import com.kd.xxhyf.util.Connection;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static com.kd.xxhyf.constant.staticConst.dbPrefix.FJPERFIX;


@Component
@Slf4j
@ConditionalOnProperty(name="config.implementType",havingValue = "fujian")
public class FjMaxIdServer {

	@Autowired
	private Connection connection;

	@Value("${config.rediskey}")
	private String REDISKEY;
	
	@Autowired
	private Synchro synchro;
	
	@Autowired
	private Notice notice;
	
	@Autowired
	private JedisCommands jedis;
	
	/**
	 * 服务启停
	 */
	@SuppressWarnings("static-access")
	@PostConstruct
	public void start(){

		Map<String,String> map =jedis.hgetAll("hash1");
		System.out.println("+++++"+map.toString());
		
		//初始化codis中存储的静态模型的最大ID,用于静态模型入库时获取ID使用，避免多线程导致ID重复问题
		/*String redisType = codisPool.getRedisType();
		if ("2".equals(redisType)) {
			codisPool.build();
		}*/
		try {
			String sql = "SELECT A.EN_TABLENAME,A.ID,B.FILEDNAME FROM "+FJPERFIX+"SYS_TABLEINFO AS A INNER JOIN  "+FJPERFIX+"SYS_FILEDINFO AS B ON  A.ID=B.TABLE_ID AND A.ID LIKE '%0' AND A.EN_TABLENAME!='"+FJPERFIX+"RUNNING_FILE_B' AND B.FILEDNAME ='ID'";
			List<Map<String, Object>> list = connection.findForDruid(sql);
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i).get("ID")+"";
				String maxId = "SELECT MAX(SUBSTR(ID,13,6)) AS NUM FROM "+list.get(i).get("EN_TABLENAME");
				try {
					List<Map<String, Object>> list2 = connection.findForDruid(maxId);
					if(list2.size()>0){
						Object object = list2.get(0).get("NUM");
						if(object==null||"".equals(object)){
							jedis.set(REDISKEY+id+"_num", "0");
						}else{
							jedis.set(REDISKEY+id+"_num", Integer.valueOf(String.valueOf(object))+"");
						}
						//log.error("---------"+list.get(i).get("EN_TABLENAME")+"最大ID同步完成-----------");
					}else{
						jedis.set(REDISKEY+id+"_num", "0");
					}
				} catch (Exception e) {
					// TODO: handle exception
					log.error(maxId);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
	}
}
