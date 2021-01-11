package com.kd.xxhyf.mysql_redis.core;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.kd.xxhyf.entity.ompse.AueServerB;
import com.kd.xxhyf.entity.ompse.SysRedisinfo;
import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.entity.sysdba.SysFiledinfo;
import com.kd.xxhyf.mapper.AueServerBMapper;
import com.kd.xxhyf.mapper.CommonTableMapper;
import com.kd.xxhyf.mapper.SysFiledInfoMapper;
import com.kd.xxhyf.mapper.SysRedisInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kd.xxhyf.util.Connection;
import com.kd.xxhyf.util.Util;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;

@Component
@Slf4j
public class Redis_MysqlImpl {

	@Value("${config.rediskey}")
	private String REDISKEY;

	@Autowired
	private Connection connection;

	@Resource
	private SysRedisInfoMapper sysRedisInfoMapper;

	@Resource
	private SysFiledInfoMapper filedInfoMapper;

	@Resource
	private AueServerBMapper aueServerBMapper;

	@Resource
	private CommonTableMapper commonTableMapper;

	private ObjectMapper objectmapper = new ObjectMapper();

	@Qualifier("getJedisCommands")
	@Autowired
	private JedisCommands jedis;


	@Async
	public void run(SysTableinfo sysTableinfo) {
		if (null == sysTableinfo )
			return;
		// 根据表ID查询表名，表字段和字段类型
		List<SysFiledinfo> sysFiledinfos = filedInfoMapper.selectListByTableId(sysTableinfo.getId());
		if (sysFiledinfos != null && !sysFiledinfos.isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("tablename", sysTableinfo.getEnTablename()); //
			map.put("chn_tablename",sysTableinfo.getChnTablename()); //
			for (SysFiledinfo sysFiledinfo : sysFiledinfos) {
				map.put(sysFiledinfo.getFiledname(), sysFiledinfo.getType());// 将字段名作为KEY，字段类型作为value存入map
			}
			try { // 获取jedis
				if (!jedis.exists(REDISKEY + sysTableinfo.getId() + "_num")) { // 判断在redis是否存在
					// 不存在新建
					jedis.set(REDISKEY + sysTableinfo.getId() + "_num", "0");
				}
				jedis.del(REDISKEY + sysTableinfo.getId());
				jedis.hmset(REDISKEY + sysTableinfo.getId() + "", map); // {tableId:{tablename:server,id:int(20),name:varchat(20)}}
				// 添加map
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			if (sysTableinfo.getId().substring(sysTableinfo.getId().length() - 1, sysTableinfo.getId().length())
					.equals("0")) { // 判断是不是静态信表 0是静态表
				// log.info("-------------------------------");
				static_data(sysTableinfo.getId() + "");// 获取静态表数据
			}
		} else {
			log.debug(Thread.currentThread().getStackTrace()[1]
					.getLineNumber() + "list.size()==0");
		}
	}

	/**
	 * 获取静态表数据
	 *
	 * @param paramTableId
	 */
	public void static_data(String paramTableId) {
		try {// 获取到可用的redis连接
			String tablename = jedis.hget(REDISKEY + paramTableId, "tablename");// 在redis中获取表名
			if (paramTableId.equals("00020")) { // 域信息表同步这个视图的数据
				tablename = "SYS_FK";// 在redis中获取表名
			}

			String sql = "SELECT * FROM " + tablename; // 获取此表所有数据
			if (tablename.equals("SG_SCS_EQUIPMENT_B")) {
				System.out.println("");
			}

			log.info("redis开始同步----" + tablename + "-----" + paramTableId);
			String jedisTablename = REDISKEY + tablename;
			List<Map<String, Object>> list = connection.findForDruid(sql);// 获取到tablename中的所有数据
			if (!tablename.equals("SYS_TABLEINFO")) {// 除了表信息表的其他静态表

				boolean is_have_key_2 = jedis.exists(jedisTablename);// 判断key是tablename的数据是否在redis中存在

				if (is_have_key_2) {
					jedis.del(jedisTablename);
				}
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);// 单独一条数据
					Map<String, String> map2 = new HashMap<String, String>();
					Set<String> set = map.keySet(); // 把集合中的Key转换为set
					for (String str : set) {
						map2.put(str, map.get(str) + ""); // 放入新map2
						// Object转String
						// 因为redis只能存String转String
					}

					if (!set.contains("NODEID")) { // 如果set中不存在nodeid
						map2.put("NODEID", map.get("ID") + ""); // 放入nodeid
					}

					boolean is_have_key = jedis.exists(map.get("ID") + "");// 判断当前数据是否存在

					if (is_have_key) {

						jedis.del(REDISKEY + map.get("ID") + "");// 删除已有的数据
						jedis.hmset(REDISKEY + map.get("ID") + "", map2);// 插入数据
						// （表中的id（主键）对应一条数据）

					} else {

						jedis.hmset(REDISKEY + map.get("ID") + "", map2);
					}

					if (map.containsKey("OBJ_ID")) {
						boolean is_have_key_obj = jedis.exists(map
								.get("OBJ_ID") + "");// 判断当前数据是否存在
						if (is_have_key_obj) {
							jedis.del(REDISKEY +paramTableId.substring(0,4)+"_"+ map.get("OBJ_ID") + "");// 删除已有的数据
							jedis.hmset(REDISKEY +paramTableId.substring(0,4)+"_"+ map.get("OBJ_ID") + "", map2);// 插入数据
							// （表中的id（主键）对应一条数据）

						} else {

							jedis.hmset(REDISKEY +paramTableId.substring(0,4)+"_"+ map.get("OBJ_ID") + "", map2);
						}

					}

					if (map.containsKey("OBJ_ID")) {
						jedis.rpush(jedisTablename,
								REDISKEY +paramTableId.substring(0,4)+"_"+ map.get("OBJ_ID") + "");// 插入数据主键ID的集合
						// {tablename：list}
					}
					jedis.rpush(jedisTablename, REDISKEY + map.get("ID")+ "");// 插入数据主键ID的集合 {tablename：list}


					// jedis.lrange("AUE_SERVER_B",0,-1);

				}
			} else { // 处理表信息表
				boolean is_have_key_2 = jedis.exists(jedisTablename);
				// List<String> list2 = jedis.lrange(jedisTablename, 0,
				// jedis.llen(jedisTablename));
				if (is_have_key_2) {
					jedis.del(jedisTablename);
				}
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					jedis.rpush(jedisTablename, REDISKEY + map.get("ID") + "");// 只把表信息表的ID（表好）放入redis
					// {tablename：list}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void synNowRunDateNeedStaticDataToCodis() {
		// 同步 CONF_COLLECTION_INDB 的 tableId
		try {

			String iSql = "SELECT DEVTYPE,EVENT_ID,SUB_EVENT_ID,TABLE_ID FROM SG_CONF_COLLECTION_INDB";
			List<Map<String, Object>> iList = connection.findForDruid(iSql);
			for (Map<String, Object> map : iList) {
				String devtype = map.get("DEVTYPE") + "";
				String eventId = map.get("EVENT_ID") + "";
				String subEvenId = map.get("SUB_EVENT_ID") + "";
				String tableId = map.get("TABLE_ID") + "";
				String key = REDISKEY + devtype + eventId + subEvenId;
				if (jedis.exists(key)) // redis存在
					jedis.del(key);
				jedis.set(key, tableId);
			}
			log.info("----同步 CONF_COLLECTION_INDB 的 tableId成功-----");

			// 同步 AUS_APP_B表 的 Id
			String appSql = "SELECT ID,VALUE,ENG_NAME FROM SG_AUS_APP_B";
			List<Map<String, Object>> appList = connection.findForDruid(appSql);
			for (Map<String, Object> map : appList) {
				String id = map.get("ID") + "";
				String value1 = map.get("VALUE") + "";
				String value2 = map.get("ENG_NAME") + "";
				String key1 = REDISKEY + "appId" + value1;
				String key2 = REDISKEY + "appId" + value2;
				if (jedis.exists(key1)) {
					// redis存在
					jedis.del(key1);
				}

				jedis.set(key1, id);
				if (jedis.exists(key2)) {
					// redis存在
					jedis.del(key2);
				}

				jedis.set(key2, id);
			}
			log.info("----同步 AUS_APP_B表 的 Id成功-----");

			// 同步 AUS_CONTEXT_B表 的 Id
			String contextSql = "SELECT ID,VALUE,ENG_NAME FROM SG_AUS_CONTEXT_B";
			List<Map<String, Object>> contextList = connection
					.findForDruid(contextSql);
			for (Map<String, Object> map : contextList) {
				String id = map.get("ID") + "";
				String value1 = map.get("VALUE") + "";
				String value2 = map.get("ENG_NAME") + "";
				String key1 = REDISKEY + "contextId" + value1;
				String key2 = REDISKEY + "contextId" + value2;
				if (jedis.exists(key1)) {
					// redis存在
					jedis.del(key1);
				}

				jedis.set(key1, id);
				if (jedis.exists(key2)) {
					// redis存在
					jedis.del(key2);
				}

				jedis.set(key2, id);

			}
			log.info("---- 同步 AUS_CONTEXT_B表 的 Id-----");

			// 同步获取服务总线模型的最大ID AUS_SERVICE_BUS_SERVER_B表 的最大 Id
			String serviceMaxIdSql = "SELECT MAX(ID) maxId FROM SG_AUS_SERVICE_BUS_SERVER_B";
			List<Map<String, Object>> serviceMaxIdList = connection
					.findForDruid(serviceMaxIdSql);
			/*
			 * if (jedis.exists("MAX_ID_AUS_SERVICE_BUS_SERVER_B")) // redis存在
			 * jedis.del("MAX_ID_AUS_SERVICE_BUS_SERVER_B");
			 */
			jedis.set(REDISKEY + "MAX_ID_AUS_SERVICE_BUS_SERVER_B",
					serviceMaxIdList.get(0).get("maxId") + "");
			log.info("---同步获取服务总线模型的最大ID AUS_SERVICE_BUS_SERVER_B表 的最大 Id----");

			// 同步 获取判断服务总线模型中是否已存在该模型（List）AUS_SERVICE_BUS_SERVER_B
			String serviceSql = "SELECT NODEID,SERVICE_NAME,P_NODE FROM SG_AUS_SERVICE_BUS_SERVER_B";
			List<Map<String, Object>> serviceList = connection
					.findForDruid(serviceSql);
			/*
			 * if (jedis.exists("IN_AUS_SERVICE_BUS_SERVER_B"))
			 * jedis.del("IN_AUS_SERVICE_BUS_SERVER_B");
			 */
			for (Map<String, Object> map : serviceList) {
				String nodeId = map.get("NODEID") + "";
				String serviceName = map.get("SERVICE_NAME") + "";
				String pNode = map.get("P_NODE") + "";
				String value = nodeId + serviceName + pNode;
				jedis.sadd(REDISKEY + "IN_AUS_SERVICE_BUS_SERVER_B", value);
			}
			log.info("--- 同步 获取判断服务总线模型中是否已存在该模型（List）AUS_SERVICE_BUS_SERVER_B---");

			// 同步应用节点模型的最大ID（AUS_APP_NODE_B）
			String apMaxIdSql = "SELECT MAX(ID) maxId FROM SG_AUS_APP_NODE_B";
			List<Map<String, Object>> apMaxIdIdList = connection
					.findForDruid(apMaxIdSql);
			/*
			 * if (jedis.exists("MAX_ID_AUS_APP_NODE_B")) // redis存在
			 * jedis.del("MAX_ID_AUS_APP_NODE_B");
			 */
			jedis.set(REDISKEY + "MAX_ID_AUS_APP_NODE_B", apMaxIdIdList.get(0)
					.get("maxId") + "");
			log.info("--- 同步应用节点模型的最大ID（AUS_APP_NODE_B）成功------");

			// 获取应用节点模型的最大ID（AUS_PROCESS_NODE_B）
			String aproMaxIdSql = "SELECT MAX(ID) maxId FROM SG_AUS_PROCESS_NODE_B";
			List<Map<String, Object>> aproMaxIdList = connection
					.findForDruid(aproMaxIdSql);
			/*
			 * if (jedis.exists("MAX_ID_AUS_PROCESS_NODE_B")) // redis存在
			 * jedis.del("MAX_ID_AUS_PROCESS_NODE_B");
			 */
			jedis.set(REDISKEY + "MAX_ID_AUS_PROCESS_NODE_B", aproMaxIdList
					.get(0).get("maxId") + "");
			log.info("--- 同步应用节点模型的最大ID（MAX_ID_AUS_PROCESS_NODE_B）成功------");

			// 同步 获取判断应用节点模型中是否已存在该模型（List）AUS_APP_NODE_B
			String appNodeSql = "SELECT CONTEXT_ID,APP_ID,NODEID,P_NODE FROM SG_AUS_APP_NODE_B";
			List<Map<String, Object>> appNodeList = connection
					.findForDruid(appNodeSql);
			// if (jedis.exists("IN_AUS_APP_NODE_B")) // redis存在
			// jedis.del("IN_AUS_APP_NODE_B");
			for (Map<String, Object> map : appNodeList) {
				String contextId = map.get("CONTEXT_ID") + "";
				String nodeId = map.get("NODEID") + "";
				String pNode = map.get("P_NODE") + "";
				String appId = map.get("APP_ID") + "";
				String value = contextId + appId + nodeId + pNode;
				jedis.sadd(REDISKEY + "IN_AUS_APP_NODE_B", value);
			}
			log.info("---同步应用节点模型的最大ID（AUS_APP_NODE_B）成功------");

			// 获取判断应用节点模型中是否已存在该模型（List）AUS_PROCESS_NODE_B
			String aproNodeSql = "SELECT CONTEXT_ID,APP_ID,NODEID,PROCESS_ID FROM SG_AUS_PROCESS_NODE_B";
			List<Map<String, Object>> aproNodeList = connection
					.findForDruid(aproNodeSql);
			/*
			 * if (jedis.exists("IN_AUS_PROCESS_NODE_B")) // redis存在
			 * jedis.del("IN_AUS_PROCESS_NODE_B");
			 */
			for (Map<String, Object> map : aproNodeList) {
				String contextId = map.get("CONTEXT_ID") + "";
				String nodeId = map.get("NODEID") + "";
				String processId = map.get("PROCESS_ID") + "";
				String appId = map.get("APP_ID") + "";
				String value = contextId + appId + nodeId + processId;
				jedis.sadd(REDISKEY + "IN_AUS_PROCESS_NODE_B", value);
			}
			log.info("---同步应用节点模型的最大ID（IN_AUS_PROCESS_NODE_B）成功------");

			// 同步 （List）AUS_HISDB_SYSTEM_NODE_B 的id
			String hisdbSql = "SELECT ID FROM SG_AUS_HISDB_SYSTEM_NODE_B";
			List<Map<String, Object>> hisdbList = connection
					.findForDruid(hisdbSql);
			/*
			 * if (jedis.exists("IN_HISDB_NODE_B")) // redis存在
			 * jedis.del("IN_HISDB_NODE_B");
			 */
			for (Map<String, Object> map : hisdbList) {
				String id = map.get("ID") + "";
				jedis.sadd(REDISKEY + "IN_HISDB_NODE_B", id);
			}
			log.info("---同步 （List）AUS_HISDB_SYSTEM_NODE_B 的id成功------");

			// 同步NODEID(String类型) AUE_SERVER_B
			String serverSql = "SELECT ID,IP,HOSTNAME,NAME FROM SG_AUE_SERVER_B";
			List<Map<String, Object>> serverList = connection
					.findForDruid(serverSql);
			for (Map<String, Object> map : serverList) {
				String id = map.get("ID") + "";
				String name = REDISKEY + map.get("HOSTNAME");
				String ip = REDISKEY
						+ Util.getMD5String("OMPSE" + map.get("IP"));

				if (jedis.exists(name)) // redis存在
					jedis.del(name);
				jedis.set(name, id);
				if (jedis.exists(ip)) // redis存在
					jedis.del(ip);
				jedis.set(ip, id);

			}
			log.info("---同步NODEID(String类型) AUE_SERVER_B成功------");

			// 获取ID(String类型)AUS_PROCESS_B表
			String processSql = "SELECT ID,NAME,ENG_NAME FROM SG_AUS_PROCESS_B";
			List<Map<String, Object>> processList = connection
					.findForDruid(processSql);
			for (Map<String, Object> map : processList) {
				String id = map.get("ID") + "";
				String name1 = REDISKEY + "processId" + map.get("NAME");
				String name2 = REDISKEY + "processId" + map.get("ENG_NAME");
				if (jedis.exists(name1)) // redis存在
					jedis.del(name1);
				jedis.set(name1, id);

				if (jedis.exists(name2)) // redis存在
					jedis.del(name2);
				jedis.set(name2, id);
			}
			log.info("---同步ID(String类型) AUS_PROCESS_B------");

			// 同步采集项配置信息（Hash类型）CONF_COLLECTION_TARGET
			String cSql = "SELECT * FROM SG_CONF_COLLECTION_TARGET";
			List<Map<String, Object>> cList = connection.findForDruid(cSql);

			for (Map<String, Object> map : cList) {
				String tableId = (map.get("TABLE_ID") + "").trim();
				String key1 = REDISKEY + "target" + tableId;
				String key2 = REDISKEY + "TARGET-ALARM-" + tableId;
				if (jedis.exists(key1)) // redis存在
					jedis.del(key1);
				if (jedis.exists(key2)) // redis存在
					jedis.del(key2);
			}
			for (Map<String, Object> map : cList) {
				String tableId = (map.get("TABLE_ID") + "").trim();
				String phNo = map.get("PH_NO") + "";
				String alarmFlag = map.get("ALARM_FLAG") + "";
				String fieldName = map.get("FIELDNAME") + "";
				String key1 = REDISKEY + "target" + tableId;
				String key2 = REDISKEY + "TARGET-ALARM-" + tableId;

				jedis.hset(key1, phNo, fieldName);
				jedis.hset(key2, fieldName, alarmFlag);

			}

			log.info("---同步采集项配置信息（Hash类型）CONF_COLLECTION_TARGET成功------");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 同步视图
	 * FIXME
	 */
	public void syn_view() {

		try {// 获取到可用的redis连接
			// 同步ALARMMODEL
			String amSql = "SELECT * FROM SG_ALARMMODEL";
			List<Map<String, Object>> amList = connection.findForDruid(amSql);
			Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "ALARMMODEL"))
				jedis.del(REDISKEY + "ALARMMODEL");
			for (Map<String, Object> m : amList) {
				String id = REDISKEY + "ALARMMODEL-" + m.get("ITEMID");
				jedis.lpush(REDISKEY + "ALARMMODEL", id);
				Set<Entry<String, Object>> entrySet = m.entrySet();
				for (Map.Entry<String, Object> entry : entrySet) {// 遍历集合放入map
					mapInfo.put(entry.getKey(), entry.getValue() + "");
				}
				if (jedis.exists(id))
					jedis.del(id);
				jedis.hmset(id, mapInfo);
			}
			log.info("---同步视图ALARMMODEL成功------");

			// 同步VIEW_HISDB_MODEL_DATA
			String vhmSql = "SELECT * FROM SG_VIEW_HISDB_MODEL_DATA";
			List<Map<String, Object>> vhmList = connection.findForDruid(vhmSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "VIEW_HISDB_MODEL_DATA"))
				jedis.del(REDISKEY + "VIEW_HISDB_MODEL_DATA");
			for (Map<String, Object> m : vhmList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "VIEW_HISDB_MODEL_DATA",
						m.get("ID") + "", json);

			}
			log.info("---同步视图VIEW_HISDB_MODEL_DATA成功------");

			// Hash类型同步数据库视图VIEW_REALDB_MODEL_DATA
			String vrmSql = "SELECT * FROM SG_VIEW_REALDB_MODEL_DATA";
			List<Map<String, Object>> vrmList = connection.findForDruid(vrmSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "VIEW_REALDB_MODEL_DATA"))
				jedis.del(REDISKEY + "VIEW_REALDB_MODEL_DATA");
			for (Map<String, Object> m : vrmList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "VIEW_REALDB_MODEL_DATA", m.get("ID")
						+ "", json);
			}
			log.info("---同步视图VIEW_HISDB_MODEL_DATA成功------");

			// Hash类型同步数据库视图APPMODEL
			String apmSql = "SELECT * FROM SG_APPMODEL";
			List<Map<String, Object>> apmList = connection.findForDruid(apmSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "APPMODEL"))
				jedis.del(REDISKEY + "APPMODEL");
			for (Map<String, Object> m : apmList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "APPMODEL", m.get("ID") + "", json);
			}
			log.info("---同步视图APPMODEL成功------");

			// Hash类型同步数据库视图 PROCESSMODEL
			String pmSql = "SELECT * FROM SG_PROCESSMODEL";
			List<Map<String, Object>> pmList = connection.findForDruid(pmSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "PROCESSMODEL"))
				jedis.del(REDISKEY + "PROCESSMODEL");
			for (Map<String, Object> m : pmList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "PROCESSMODEL", m.get("NODEPROCESSID")
						+ "", json);
			}
			log.info("---同步视图PROCESSMODEL成功------");

			// Hash类型同步数据库视图 VIEW_SERVICE_FRIST
			String vsfSql = "SELECT * FROM SG_VIEW_SERVICE_FRIST";
			List<Map<String, Object>> vsfList = connection.findForDruid(vsfSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "VIEW_SERVICE_FRIST"))
				jedis.del(REDISKEY + "VIEW_SERVICE_FRIST");
			for (Map<String, Object> m : vsfList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "VIEW_SERVICE_FRIST", m.get("SERVICEID")
						+ "", json);
			}
			log.info("---同步视图PROCESSMODEL成功------");

			// 获取采集项配置信息（Hash类型）VIEW_REALDB_ALARM_MODEL_DATA
			String vramdSql = "SELECT * FROM SG_VIEW_REALDB_ALARM_MODEL_DATA";
			List<Map<String, Object>> vramdList = connection
					.findForDruid(vramdSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "VIEW_REALDB_ALARM_MODEL_DATA"))
				jedis.del(REDISKEY + "VIEW_REALDB_ALARM_MODEL_DATA");
			for (Map<String, Object> m : vramdList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "VIEW_REALDB_ALARM_MODEL_DATA",
						m.get("JUDGE_ID") + "", json);
			}
			log.info("---同步视图VIEW_REALDB_ALARM_MODEL_DATA成功------");

			// 获取采集项配置信息（Hash类型）VIEW_REALDB_ALARM_MODEL_DATA
			String fkSql = "SELECT * FROM SG_SYS_FK";
			List<Map<String, Object>> fkList = connection.findForDruid(fkSql);
			// Map<String, String> mapInfo = new HashMap<>();
			if (jedis.exists(REDISKEY + "VIEW_SYS_FK"))
				jedis.del(REDISKEY + "VIEW_SYS_FK");
			for (Map<String, Object> m : fkList) {
				String json = objectmapper.writeValueAsString(m);
				jedis.hset(REDISKEY + "VIEW_SYS_FK", m.get("ID") + "", json);
			}
			log.info("---同步视图VIEW_SYS_FK成功------");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 获取配置信息
	 *
	 */
	public void runing_data() {
		List<SysRedisinfo> sysRedisinfos = sysRedisInfoMapper.selectList(null);// 同步告警阈值的 codis查询表
		try {
			jedis.del("running_model");
			Map<String, String> fmap = new HashMap<String, String>();
			for (SysRedisinfo sysRedisinfo : sysRedisinfos) {
				String id = sysRedisinfo.getId();// 主键
				String descp = sysRedisinfo.getDescp();// 描述
				String sql = sysRedisinfo.getMysql().toUpperCase();// SQL语句
				String fid = sysRedisinfo.getPid();
				String key = sysRedisinfo.getKey();
				String hkey = sysRedisinfo.getHkey();
				String hvalue = sysRedisinfo.getHvalue();

				if (jedis.exists(REDISKEY + id + "-SYS_REDISINFO"))
					jedis.del(REDISKEY + id + "-SYS_REDISINFO");

				fmap.put(sql, descp); // sql为key,sql的描述为 value
				// jedis.hdel(fid, sql);
				String keys[] = key.split(",");
				String hkeys[] = hkey.split(",");
				String hvalues[] = hvalue.split(",");
				jedis.del(REDISKEY + sql);
				jedis.del(REDISKEY + sql + "_key");
				for (int i = 0; i < keys.length; i++) {
					jedis.rpush(REDISKEY + sql + "_key", keys[i]);// 放入list
					Map<String, String> map2 = new HashMap<String, String>();
					if (hkeys[i].equals("ALL")) {
						map2.put("key", "0");
						map2.put("value", "0");
					} else {
						map2.put("key", hkeys[i]);
						map2.put("value", hvalues[i]);
					}
					jedis.hmset(REDISKEY + sql + "_rule_" + keys[i], map2);
				}

				List<String> rkeys = jedis.lrange(REDISKEY + sql + "_key",
						0, jedis.llen(REDISKEY + sql + "_key")); // 取出 keys
				for (int i = 0; i < rkeys.size(); i++) {
					String rkey = rkeys.get(i);
					String rule = REDISKEY + sql + "_rule_" + rkey;
					Map<String, String> rmap = jedis.hgetAll(rule); // 拿出所有的数据
					String key_2 = rmap.get("key");
					String value_2 = rmap.get("value");
					List<Map<String, Object>> list = connection
							.findForDruid(sql);
					for (int k = 0; k < list.size(); k++) {
						Map<String, String> redismap = new HashMap<String, String>();
						Map<String, Object> map_2 = list.get(k);
						Set<String> set = map_2.keySet();
						if (key_2.equals("0")) {
							for (String string : set) {
								redismap.put(string, map_2.get(string) + "");
							}
							jedis.hmset(REDISKEY + map_2.get(rkey) + "",
									redismap);
							jedis.rpush(REDISKEY + sql, map_2.get(rkey)
									+ "");
							jedis.rpush(REDISKEY + id + "-SYS_REDISINFO",
									map_2.get("ID") + "");
						} else {
							redismap.put(map_2.get(key_2) + "",
									map_2.get(value_2) + "");
							// jedis.del(map_2.get(rkey) + "");

							jedis.hmset(REDISKEY + map_2.get(rkey) + "",
									redismap);
							jedis.rpush(REDISKEY + id + "-SYS_REDISINFO",
									map_2.get("ID") + "");
						}
					}
				}
				jedis.hmset(REDISKEY + "running_model", fmap);

			}
			// jedis.hmset("running_model", fmap);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * 同步server表
	 */
	public void server() {
		List<AueServerB> aueServerBList = aueServerBMapper.selectListWhereNameNotNull();
		try {

			if (jedis.exists(REDISKEY + "AUE_SERVER_B_DATA")) {
				jedis.del(REDISKEY + "AUE_SERVER_B_DATA");
			}

			if(jedis.exists(REDISKEY + "AUE_SERVER_B_DATA")) {
				jedis.del(REDISKEY + "AUE_SERVER_B_DATA");
			}

			for (AueServerB aueServerB : aueServerBList) {
				String json = objectmapper.writeValueAsString(aueServerB);
				jedis.hset(REDISKEY + "AUE_SERVER_B_DATA", aueServerB.getId(),json);
				jedis.hset(REDISKEY + "SERVER_IP_DATA", aueServerB.getIp() + "",aueServerB.getId());
			}
		} catch (Exception e) {
			log.error("Redis_Mysql.server错误",e);
		}
	}

	/**
	 * 同步deviceId
	 */
	public void synDeviceId() {
		// String sql =
		// "SELECT  *  FROM SG_AUE_SERVER_B WHERE NAME IS NOT NULL";
		// List<Map<String, Object>> list = connection.findForDruid(sql);
		try {
			List<String> deviceId = new ArrayList<String>();
			List<Map<String, Object>> deviceList = new ArrayList<>();
			String[] safeAreaIdArrays = { "1001", "1002", "1003" };

			for (int j = 0; j < safeAreaIdArrays.length; j++) {
				deviceId = new ArrayList<String>();
				String safeAreaId = safeAreaIdArrays[j];
				jedis.del(REDISKEY + "DEVICEID_" + safeAreaId);
				// 获取deviceId
				// 进程
				String processSql = "SELECT APNB.ID  FROM SG_AUE_SERVER_B AS ASB, SG_AUS_PROCESS_NODE_B AS APNB WHERE ASB.ID=APNB.NODEID AND ASB.SECURITYAREA= '"
						+ safeAreaId + "'";
				deviceList = connection.findForDruid(processSql);
				for (int i = 0; i < deviceList.size(); i++) {
					deviceId.add(deviceList.get(i).get("ID").toString());
				}
				// 历史库
				String hisdbSql = "SELECT AHSNDB.ID  FROM SG_AUE_SERVER_B AS ASB, SG_AUS_HISDB_SYSTEM_NODE_B AS AHSNDB WHERE ASB.ID=AHSNDB.NODEID AND ASB.SECURITYAREA= '"
						+ safeAreaId + "'";

				deviceList = connection.findForDruid(hisdbSql);
				for (int i = 0; i < deviceList.size(); i++) {
					deviceId.add(deviceList.get(i).get("ID").toString());
				}
				// 服务总线
				String serviceSql = "SELECT ASBSB.ID  FROM SG_AUE_SERVER_B AS ASB, SG_AUS_SERVICE_BUS_SERVER_B AS ASBSB WHERE ASB.ID=ASBSB.NODEID AND ASB.SECURITYAREA= '"
						+ safeAreaId + "'";
				deviceList = connection.findForDruid(serviceSql);
				for (int i = 0; i < deviceList.size(); i++) {
					deviceId.add(deviceList.get(i).get("ID").toString());
				}

				// 应用
				String appSql = "SELECT AANB.ID  FROM SG_AUE_SERVER_B AS ASB, SG_AUS_APP_NODE_B AS AANB WHERE ASB.ID=AANB.NODEID AND ASB.SECURITYAREA= '"
						+ safeAreaId + "'";

				deviceList = connection.findForDruid(appSql);
				for (int i = 0; i < deviceList.size(); i++) {
					deviceId.add(deviceList.get(i).get("ID").toString());
				}

				// 服务器
				String serverSql = "SELECT ID  FROM SG_AUE_SERVER_B  WHERE SECURITYAREA= '"
						+ safeAreaId + "'";

				deviceList = connection.findForDruid(serverSql);
				for (int i = 0; i < deviceList.size(); i++) {
					deviceId.add(deviceList.get(i).get("ID").toString());
				}

				// 实时库
				String realdbSql = "SELECT ARB.ID  FROM SG_AUE_SERVER_B AS ASB, SG_AUS_REALDB_B AS ARB WHERE ASB.ID=ARB.NODEID AND ASB.SECURITYAREA= '"
						+ safeAreaId + "'";

				deviceList = connection.findForDruid(realdbSql);
				for (int i = 0; i < deviceList.size(); i++) {
					deviceId.add(deviceList.get(i).get("ID").toString());
				}
				// String[] strings = new String[deviceId.size()];
				jedis.lpush(REDISKEY + "DEVICEID_" + safeAreaId,
						deviceId.toArray(new String[deviceId.size()]));

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
