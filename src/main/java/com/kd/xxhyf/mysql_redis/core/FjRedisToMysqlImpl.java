package com.kd.xxhyf.mysql_redis.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.util.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kd.xxhyf.constant.staticConst.dbPrefix.FJPERFIX;

@Component
@Slf4j
@ConditionalOnProperty(name="config.implementType",havingValue = "fujian")
public class FjRedisToMysqlImpl implements RedisToMysql{

	@Value("${config.rediskey}")
	private String REDISKEY;

	@Autowired
	private Connection connection;

	private ObjectMapper objectmapper = new ObjectMapper();

	@Autowired
	private JedisCommands jedis;
	
	public FjRedisToMysqlImpl(){
	}

	
	@Override
	@Async
	public void run(SysTableinfo sysTableinfo) {
		// TODO Auto-generated method stub  sys_filedinfo
		String value = sysTableinfo.getId();
		String filedname = "SELECT B.FILEDNAME,B.TYPE,A.EN_TABLENAME  FROM "+FJPERFIX+"SYS_TABLEINFO AS A,"+FJPERFIX+"SYS_FILEDINFO AS B WHERE A.ID = B.TABLE_ID AND A.ID = '" + value + "'";//查找字段的属性和表明
	
		List<Map<String, Object>> list = connection.findForDruid(filedname);
		
		if (list.size() != 0) {
			Map<String, String> map = new HashMap<String, String>();
			
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map2 = list.get(i);
				map.put(map2.get("FILEDNAME") + "", map2.get("TYPE") + "");
				map.put("tablename", map2.get("EN_TABLENAME") + "");
			}
			try {
				if(!jedis.exists(REDISKEY+value+"_num")){
					jedis.set(REDISKEY+value+"_num", "0");
				}
				jedis.del(REDISKEY+value);
				jedis.hmset(REDISKEY+value + "", map);
				log.error(filedname+"表同步成功");
			}catch (Exception e) {
				e.getStackTrace();
				log.error(e.toString());
			}
			if (value.substring(value.length() - 1, value.length()).equals("0")) {
				static_data(value + "");//静态信息
			}
		} else {
			log.debug(Thread.currentThread().getStackTrace()[1].getLineNumber() + "list.size()==0");
		}
	}
	
	
	/**
	 * 获取静态表数据
	 * 
	 * @param value
	 */
	@Override
	public void static_data(String value) {
			if (value.substring(value.length() - 1, value.length()).equals("0")) {//判断value是否是静态表，此处不匹配采集信息表中的数据
				try  {//获取到可用的redis连接
					String tablename = jedis.hget(REDISKEY+value, "tablename");//在redis中获取表名
					String sql = "SELECT * FROM " + tablename;
					
					List<Map<String, Object>> list = connection.findForDruid(sql);//获取到tablename中的所有数据
					
					if (!tablename.equals(FJPERFIX+"SYS_TABLEINFO")) {//表信息表需要单独处理
						
						boolean is_have_key_2 = jedis.exists(REDISKEY+tablename);//判断key是tablename的数据是否在redis中存在
				
						if (is_have_key_2) {
							jedis.del(REDISKEY+tablename);
						}
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> map = list.get(i);//单独一条数据
							Map<String, String> map2 = new HashMap<String, String>();
							Set<String> set = map.keySet();
							for (String str : set) {
								map2.put(str, map.get(str) + "");
							}
							if(!set.contains("NODEID")){
								map2.put("NODEID", map.get("ID")+"");
							}
							
							boolean is_have_key = jedis.exists(REDISKEY+map.get("ID") + "");//判断当前数据是否存在
							if (is_have_key) {
								jedis.del(REDISKEY+map.get("ID") + "");//删除已有的数据
								jedis.hmset(REDISKEY+map.get("ID") + "", map2);//插入数据
							} else {
								jedis.hmset(REDISKEY+map.get("ID") + "", map2);
							}
							jedis.rpush(REDISKEY+tablename, REDISKEY+map.get("ID") + "");//插入数据主键ID的集合
						}
					}else{
						boolean is_have_key_2 = jedis.exists(REDISKEY+tablename);
						//List<String> list2 = jedis.lrange(tablename, 0, jedis.llen(tablename));
						if (is_have_key_2) {
							jedis.del(REDISKEY+tablename);
						}
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> map = list.get(i);
							jedis.rpush(REDISKEY+tablename, REDISKEY+map.get("ID") + "");
						}
					}
			}catch (Exception e) {
				e.getStackTrace();
				log.error(e.toString());
			}
		}
	}

	@Override
	public void synNowRunDateNeedStaticDataToCodis() {

	}

	/**
	 * 获取配置信息
	 * 
	 * @param value 为SQL
	 */
	public void runing_data(String value) {
		String sql2 = "SELECT * FROM "+FJPERFIX+"SYS_REDISINFO ";
		List<Map<String, Object>> lists = connection.findForDruid(sql2);
		
		try {
			jedis.del("running_model");
			Map<String, String> fmap = new HashMap<String, String>();
			for (int j = 0; j < lists.size(); j++) {
				Map<String, Object> data = lists.get(j);
				String descp = data.get("DESCP") + "";//描述
				String sql = (data.get("MYSQL") + "").toUpperCase();//SQL语句
				String fid = data.get("PID") + "";
				String key = data.get("KEY") + "";
				String hkey = data.get("HKEY") + "";
				String hvalue = data.get("HVALUE") + "";
				if("null".equals(value)){
					fmap.put(sql, descp);
					//jedis.hdel(fid, sql);
					String keys[] = key.split(",");
					String hkeys[] = hkey.split(",");
					String hvalues[] = hvalue.split(",");
					jedis.del(REDISKEY+sql);
					jedis.del(REDISKEY+sql + "_key");
					for (int i = 0; i < keys.length; i++) {
						jedis.rpush(REDISKEY+sql + "_key", keys[i]);
						Map<String, String> map2 = new HashMap<String, String>();
						if (hkeys[i].equals("ALL")) {
							map2.put("key", "0");
							map2.put("value", "0");
						} else {
							map2.put("key", hkeys[i]);
							map2.put("value", hvalues[i]);
						}
						jedis.hmset(REDISKEY+sql + "_rule_" + keys[i], map2);
					}

					List<String> rkeys = jedis.lrange(REDISKEY+sql + "_key", 0,jedis.llen(REDISKEY+sql + "_key"));
					for (int i = 0; i < rkeys.size(); i++) {
						String rkey = rkeys.get(i);
						String rule = REDISKEY+sql + "_rule_" + rkey;
						Map<String, String> rmap = jedis.hgetAll(rule);
						String key_2 = rmap.get("key");
						String value_2 = rmap.get("value");
						List<Map<String, Object>> list = connection.findForDruid(sql);
						for (int k = 0; k < list.size(); k++) {
							Map<String, String> redismap = new HashMap<String, String>();
							Map<String, Object> map_2 = list.get(k);
							Set<String> set = map_2.keySet();
							if (key_2.equals("0")) {
								for (String string : set) {
									redismap.put(string, map_2.get(string) + "");
									
								
								}
								//jedis.del(map_2.get(rkey) + "");
								jedis.hmset(REDISKEY+map_2.get(rkey), redismap);
								log.error("---存取sql结果全部数据 ID  为 "+map_2.get(rkey)+" : "+  redismap );
								jedis.rpush(REDISKEY+sql, map_2.get(rkey) + "");
							} else {
								redismap.put(map_2.get(key_2) + "",map_2.get(value_2) + "");
								jedis.hmset(REDISKEY+map_2.get(rkey), redismap);
							}
						}
					}
				}else{
					if(sql.indexOf(jedis.hget(REDISKEY+value, "tablename"))>=0){
						Map<String, String> map = new HashMap<String, String>();
						map.put(sql, descp);
						jedis.hdel(REDISKEY+fid, sql);
						jedis.hmset(REDISKEY+fid, map);
						String keys[] = key.split(",");
						String hkeys[] = hkey.split(",");
						String hvalues[] = hvalue.split(",");
						jedis.del(REDISKEY+sql);
						jedis.del(REDISKEY+sql + "_key");
						for (int i = 0; i < keys.length; i++) {
							jedis.rpush(REDISKEY+sql + "_key", keys[i]);
							Map<String, String> map2 = new HashMap<String, String>();
							if (hkeys[i].equals("ALL")) {
								map2.put("key", "0");
								map2.put("value", "0");
							} else {
								map2.put("key", hkeys[i]);
								map2.put("value", hvalues[i]);
							}
							jedis.hmset(REDISKEY+sql + "_rule_" + keys[i], map2);
						}
						List<String> rkeys = jedis.lrange(REDISKEY+sql + "_key", 0,jedis.llen(REDISKEY+sql + "_key"));
						for (int i = 0; i < rkeys.size(); i++) {
							String rkey = rkeys.get(i);
							String rule = REDISKEY+sql + "_rule_" + rkey;
							Map<String, String> rmap = jedis.hgetAll(REDISKEY+rule);
							String key_2 = rmap.get("key");
							String value_2 = rmap.get("value");
							List<Map<String, Object>> list = connection.findForDruid(sql);
							for (int k = 0; k < list.size(); k++) {
								Map<String, String> redismap = new HashMap<String, String>();
								Map<String, Object> map_2 = list.get(k);
								Set<String> set = map_2.keySet();
								if (key_2.equals("0")) {
									for (String string : set) {
										redismap.put(string, map_2.get(string) + "");
									}
									log.warn(map_2.get(rkey) + "");
									jedis.hmset(REDISKEY+map_2.get(rkey) + "", redismap);
									jedis.rpush(REDISKEY+sql, map_2.get(rkey) + "");
								} else {
									redismap.put(map_2.get(key_2) + "",map_2.get(value_2) + "");
									//jedis.del(map_2.get(rkey) + "");
									jedis.hmset(REDISKEY+map_2.get(rkey) + "", redismap);
								}
							}
						}

					}
				}
			}
			jedis.hmset(REDISKEY+"running_model", fmap);
		} catch (Exception e) {
			e.getStackTrace();
			log.error(e.toString());
		}
	}

	@Override
	public void server(){
		String sql = "SELECT NODE_NAME , ID AS NODEID FROM "+FJPERFIX+"AUE_SERVER_B WHERE NODE_NAME IS NOT NULL "
				+ "UNION ALL "
				+ "SELECT NODE_NAME , ID AS NODEID FROM "+FJPERFIX+"AUE_WORKSTATION_B WHERE NODE_NAME IS NOT NULL";
		List<Map<String, Object>> list = connection.findForDruid(sql);
		try {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("NODE_NAME", list.get(i).get("NODE_NAME")+"");
				map.put("NODEID", list.get(i).get("NODEID")+"");
				jedis.hmset(REDISKEY+list.get(i).get("NODE_NAME")+"",map);
			}
			//log.error("------同步server表完毕-------");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void synDeviceId() {

	}

	/**
	 * 同步视图
	 */
	@Override
	public void syn_view() {

		try {// 获取到可用的redis连接

			// Hash类型同步数据库视图APPMODEL
			String apmSql = "SELECT * FROM " + FJPERFIX + "APP_MODEL";
			List<Map<String, Object>> apmList = connection.findForDruid(apmSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "APPMODEL"))
				jedis.del(REDISKEY + "APPMODEL");
			for (Map<String, Object> m : apmList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "APPMODEL", m.get("ID") + "", json);
			}
			log.error("---同步视图APPMODEL成功------");
			// Hash类型同步数据库视图 PROCESSMODEL
			String pmSql = "SELECT * FROM " + FJPERFIX + "PROCESS_MODEL";
			List<Map<String, Object>> pmList = connection.findForDruid(pmSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "PROCESSMODEL"))
				jedis.del(REDISKEY + "PROCESSMODEL");
			for (Map<String, Object> m : pmList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "PROCESSMODEL", m.get("NODEPROCESSID")
						+ "", json);
			}
			log.error("---同步视图PROCESSMODEL成功------");

			

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void runing_data() {

	}

}
