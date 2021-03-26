package com.kd.xxhyf.static_model.core;

import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.util.Connection;
import com.kd.xxhyf.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.text.DecimalFormat;
import java.util.*;

import static com.kd.xxhyf.constant.staticConst.dbPrefix.FJPERFIX;

/**
 * 
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:43:53
 *
 */
@Component
@Slf4j
@ConditionalOnProperty(name="config.implementType",havingValue = "fujian")
public class FjStaticServiceImpl implements StaticService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticServiceImpl.class);

	private String receiveString;

	@Value("${config.rediskey}")
	private String REDISKEY;

	@Autowired
	private Connection connection;

	private RedisConfig redisConfig;
	@SuppressWarnings("static-access")
	private JedisCluster jedis=redisConfig.getJedisCluster();
	public static Set<String> set = new HashSet<>();

	public FjStaticServiceImpl(){

	}


	/**
	 * 多线程处理静态信息
	 */
	@Override
	public void run(String receiveString) {
		// TODO Auto-generated method stub
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String,Object>>>();
		try {
			map = XmlUtil.statusXml(receiveString);//解析XML,组合成map集合
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(receiveString+"---"+e.getMessage());
		}
		Set<String> ids=map.keySet();//获取map中的所有key
		LOGGER.debug("解析XML获取的数据KEY的个数:"+ids.size());
		for (String key : ids) { //遍历key
			String[] id = key.split("_");//key是设备ID和表号 组成的
			String device=id[0];//设备ID
			String type=  id.length >=3 ? id[2]:id[1] ;
			String index=  id.length >=3 ? id[1]:"" ;
			
			Map<String,String> redismap = new HashMap<String,String>();
			//获取jedis连接
			redismap.putAll(jedis.hgetAll(REDISKEY+type));//获取到相应表号的 域信息
			if(!"".equals(device.trim())){//判断XML里的域是否在域信息表中存在
				if(type.equals("101300")){//更新表数据
					update1(device, type, map.get(key));
				}else if("100540".equals(type)&&!"".equals(index)) {
					update2(device, type, index,map.get(key),redismap);
				}else{
					update(device, type,map.get(key),redismap);
				}
				
				
			}else{
				  insert_data(device, type, map.get(key),redismap);//插入到静态模型表
				
			}
			
		}
	}
	/**
	 * 插入
	 * @param device
	 * @param type
	 * @param list
	 */
	public void insert_data(String device,String type,List<Map<String, Object>> list ,Map<String,String> redismap){
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String col = ""; //拼接插入SQL中的字段
				String value = "";//拼接插入SQL中的 值
				String where = "";//拼接插入SQL中的 where条件
				String update = "";//拼接更新SQL
				Set<String> set = map.keySet();
				String tablename = redismap.get("tablename");
				String insert="INSERT INTO "+tablename+" (";
				String select = "SELECT * FROM "+tablename+" WHERE 1=1 ";
				//用来截取 区号  xml 标签里必须有  ID或DEVICE_ID或NODEID 其一
				String nodeid = "";//节点ID
				
				Map<String, String> upMap = new HashMap<String, String>();//upMap进行判定update 哪个字段的数据;
				
				String upMapCol = "";//where条件判断的字段 一般 是nodeid或者是ID再或者是DEVICE_ID
				
				boolean b = true;//如果某字典表中缺少信息 b=false 将不插入到静态模型表中
				
				 if(map.containsKey("DEVICE_ID")){
					 nodeid = map.get("DEVICE_ID")+"";
					 device = nodeid;
					 upMapCol = "DEVICE_ID";
				 }else{
					 if(map.containsKey("NODEID")){
						 nodeid = map.get("NODEID").toString();
						 upMapCol = "NODEID";
						 if(nodeid.length()>18){
							 nodeid = nodeid.substring(0, 18);
						 }
						 
						 try {
							 nodeid = jedis.hget(REDISKEY+nodeid, "NODEID");
						 }catch (Exception e) {
							// TODO: handle exception
						}
					 }else{
						    nodeid = map.get("ID")+"";
							upMapCol = "ID";
							/*if("".equals(nodeid)||"null".equals(nodeid)||nodeid==null){
								LOGGER.warn(type+":"+map.toString()+"节点ID是空 无法确定区域号！！！！");
								break;
							}*/
					 }
					 
				 }
				 if("".equals(nodeid)||"null".equals(nodeid)||nodeid==null){
						//LOGGER.warn(type+":"+map.toString()+"节点ID是空 无法确定区域号！！！！");
						break;
					}
				for (String key : set) {
					//log.error(key.toUpperCase());
					//log.error(jedis.hgetAll(type));
					//Boolean boolean1 = jedis.hexists(type, key.toUpperCase());
					Boolean boolean1 = redismap.containsKey(key.toUpperCase());
					 if(boolean1){
						 //判断表中是否已存在的字段(根据redis)如果存在更新数据库，redis插入
						 //判断该字段是否是引用显示
						 if(key.equals("ID")){
							 continue;
						 }
						 String v = null;
						 try {
							 v = value(type, key, map.get(key)+"",nodeid.length()>18?nodeid.substring(0,18):nodeid);
						} catch (Exception e) {
							// TODO: handle exception
							//LOGGER.warn(receiveString);
						
						}
						 
						 if(v!=null){
							 v= v.replaceAll( "\\\\",   "\\\\\\\\");
							
//							 if(key.equals("NODEID")){
//								 value +="'"+map.get("NODEID")+"',";
//								 col +=key+",";
//								 upMap.put(key, map.get("NODEID")+"");
//								 update +=key+"='"+map.get("NODEID")+"' ,";
//								 where += "AND "+key +"='"+map.get("NODEID")+"' ";
							 if(key.equals("NODEID")){
								 value +="'"+v+"',";
								 col +=key+",";
								 upMap.put(key, v+"");
								 update +=key+"='"+v+"' ,";
								 where += "AND "+key +"='"+v+"' ";
							 }else {
								 value +="'"+v+"',";
								 col +=key+",";
								 upMap.put(key, v);
								 update +=key+"='"+v+"' ,";
								 if(!key.equals("TIME")){
									 where += "AND "+key +"='"+v+"' ";
								 }
							}
							
						 }else{
							 
							 if("null".equals(map.get(key)+"")||"".equals(map.get(key)+"")||null==map.get(key)){
								 //LOGGER.error(key+"数据为空将不做处理"+receiveString);
							 }else{
								// log.error(type);
								// LOGGER.error(key+"数据为空将不做处理"+receiveString);
								 b = false;
							 }
						 }
					 }
				}
				if(b){
					DecimalFormat df = new DecimalFormat("000000");
					//String sql = select+" AND "+upMapCol+"='"+nodeid+"'";
					try {

						String new_id =type +nodeid.substring(6, 12);
						String sql = select+where;
						List<Map<String, Object>> list3 = 	connection.findForDruid(sql);
						if(list3.size()>0){
							//System.out.println(update.substring(0, update.length()-1));
							String updateSql = "UPDATE "+tablename+" SET "+update.substring(0, update.length()-1)+" WHERE 1=1 "+ where;
							connection.execute(updateSql);
						}else{
							Integer max_id = 0;
							try  {
								max_id = Integer.valueOf(String.valueOf(jedis.incr(REDISKEY+type+"_num")));
							} catch (Exception e) {
								// TODO: handle exception
								LOGGER.error(e.getMessage());
							}
							if(max_id!=0){
								try {
									new_id = new_id+df.format(max_id);
									String insertSql = insert+col+" ID) VALUES ("+value.substring(0, value.length()-1)+",'"+new_id+"')";
									connection.execute(insertSql);
								} catch (Exception e) {
									// TODO: handle exception
									LOGGER.error(e.getMessage());
								}
							}
							
						}
					} catch (Exception e) {
						LOGGER.error(e.getMessage());
					}
					
				}else{
					//LOGGER.error(tablename+"缺少信息未能成功插入!!!");
				}
			}
	}

	/**
	 * 更新
	 * @param device
	 * @param type
	 * @param list
	 */
	@Transactional
	public void update(String device,String type,List<Map<String, Object>> list,Map<String,String> redismap){
		List<String> list2 = new ArrayList<String>();
			
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				Set<String> set = map.keySet();
				String update="UPDATE "+redismap.get("tablename")+" SET ";//更新SQL
				String value = "";//更新值
				String nodeid = "";
				String upMapCol = "";//where条件判断的字段 一般 是nodeid或者是ID
				if(map.containsKey("DEVICE_ID")){
					nodeid = map.get("DEVICE_ID").toString();
					upMapCol = "DEVICE_ID";
				}else{
					upMapCol = "ID";
					if(map.containsKey("NODEID")){
						nodeid = map.get("NODEID").toString();
					}else{
						nodeid = device;
					}
					
				}
				
				boolean b=false;
				for (String key : set) {
					//判断表中是否已存在的字段(根据redis)如果存在更新数据库，redis插入
					//Boolean boolean1 = jedis.hexists(type, key.toUpperCase());
					Boolean boolean1 = redismap.containsKey(key.toUpperCase());
					 if(boolean1){
						 String v = value(type, key, map.get(key)+"",nodeid);
						 if(v!=null){
							 v= v.replaceAll( "\\\\",   "\\\\\\\\");
							 value +=key+"='"+v+"' ,";
						 }
					 }
				}
				
				update = update+value.substring(0, value.length()-1)+" WHERE "+upMapCol+" = '"+device+"' ";
				
				list2.add(update);
			}
			try {
				connection.execute(list2);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
	}
	
	
	public void update1(String device,String type,List<Map<String, Object>> list){
			String update="UPDATE FUJIAN."+FJPERFIX+"RUNNING_FILE_B SET STATUS=1 WHERE ID = '"+device+"'";
			try {
				//FIXME connection.execute_s(update);
				connection.execute(update);
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage());
			}
		
	}
	@Transactional
	public void update2(String device,String type,String index ,List<Map<String, Object>> list,Map<String,String> redismap){
		
	      List<String> list2 = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				Set<String> set = map.keySet();
				String update="UPDATE "+redismap.get("tablename")+" SET ";//更新SQL
				String value = "";//更新值
				for (String key : set) {
					Boolean boolean1 = redismap.containsKey(key.toUpperCase());
					 if(boolean1){
						 String v= map.get(key)+"";
						 value += key+"='"+v+"' ,";
					 }
				}
				update = update+value.substring(0, value.length()-1)+" "
						+ "WHERE device_id = '"+device+"' AND IF_INDEX='"+index+"' ";
				log.error(update);
				list2.add(update);
			}
			connection.execute(list2);
		
	
}
	
	
	/**
	 * 根据表号列名获取外键关联的数据ID
	 * @param tableid
	 * @param col
	 * @param value
	 * @return
	 */
	public String value(String tableid,String col,String value,String nodeid){
		
		String sql ="SELECT * FROM "+FJPERFIX+"SYS_FILEDINFO WHERE TABLE_ID = '"+tableid+"' AND FILEDNAME = '"+col+"'";
		
		List<Map<String, Object>> list = connection.findForDruid(sql);
		if(list.size()>0){
			Map<String, Object> map = list.get(0);
			String fk_id = map.get("FK_ID")+"";
			String fk_front = map.get("FK_FRONT")+"";
			if("null".equals(fk_front)||"".equals(fk_front)){
				return ""+value+"";
			}else{
				return fk_value(fk_id, fk_front, value);
			}
			
		}else{
			return ""+value+"";
		}
	}
	
	/**
	 * 获取外键关联数据
	 * @param fk_id
	 * @param fk_front
	 * @param value
	 * @return
	 */
	public String fk_value(String fk_id,String fk_front,String value ){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> filedNameMap = new HashMap<String, String>();
		try {//保证每次获取得codis连接均可用
			map = jedis.hgetAll(REDISKEY+fk_id);//从codis中获取key是fid的信息
			filedNameMap = jedis.hgetAll(REDISKEY+fk_front);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
			String table_id = map.get("TABLE_ID");//表号
			
			String fk_ids = map.get("FK_ID");//外键关联ID
			
			String fk_fronts = map.get("FK_FRONT");//外键引用入库字段
			
			String filed_name = map.get("FILEDNAME");//域英文名
			
			Map<String, String> tableNameMap = new HashMap<String, String>();
			try  {//保证每次获取得codis连接均可用
				tableNameMap = jedis.hgetAll(REDISKEY+table_id);//从codis中获取key是table_id的信息
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage());
				return null;
			}
			
			if("null".equals(fk_ids)||"null".equals(fk_fronts)){//判断本次的表数据是否是关联到其他的表数据,如果是将继续遍历,直到遍历到最后
				
				String sql = "SELECT * FROM "+tableNameMap.get("tablename")+" WHERE "+filedNameMap.get("FILEDNAME")+"='"+value+"'";
				List<Map<String, Object>> list = connection.findForDruid(sql);
				
				if(list.size()>0){//如果存在将返回相对应的关联ID,反之将进入待办任务
					
					return list.get(0).get(filed_name)+"";
				
				}else{
					
					return null;
				}
			}else{
				return fk_value(fk_ids, fk_fronts, value);
			}

	}
}
