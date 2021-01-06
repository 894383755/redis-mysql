package com.kd.xxhyf.notice.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.kd.xxhyf.util.Connection;
import com.kd.xxhyf.util.resolveXml;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 处理 待办任务service
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月20日 下午4:58:31
 *
 */
@Component
public class NoticeImpl {

	@Autowired
	private Connection connection;

	@Async
	public void run(String receiveString) {//线程启动方法
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
		
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//确定获取的当前时间是北京、上海时间
		
		Map<String, List<Map<String, Object>>> map = resolveXml.statusXml(receiveString);//解析XML
		
		Set<String> ids=map.keySet();//获取typeid deviceid
		
		for(Object value : ids){//循环set
			
		    String[] id=(value+"").split("_");//根据_分割
		    
		    String type=id[1];//获取表号
		    
		    List<Map<String, Object>> dataList=map.get(value);//根据value获取到value对应的集合
		   
		    for (int i = 0; i < dataList.size(); i++) {//进行单个数据处理
		    	
				Map<String, Object> map2 = dataList.get(i);//获取Map数据
				
				String oper_type = map2.get("OPER_TYPE")+"";//数据操作类型
				
				String data_source = map2.get("DATA_SOURCE")+"";
				
				JSONObject jsonObject = JSONObject.fromObject(map2);//将数据转换成JSON字符串
				
				String sql = "SELECT * FROM SG_SYS_NOTICE WHERE NOTICE_DESC='"+jsonObject.toString()+"' AND TABLE_ID='"+type+"'";//需要执行的SQL
				
				List<Map<String, Object>> list = connection.findForDruid(sql);//进行判断当前数据是否在数据库中存在
				
				if(list.size()==0){//只有在数据库中不存在时再执行
					
					switch (oper_type) {
					
					case "1"://新增 add
						sql = "INSERT INTO SG_SYS_NOTICE (NOTICE_DESC,TABLE_ID,START_TIME,OPT,DATA_SOURCE) VALUES('"+jsonObject.toString()+"','"+type+"','"+sdf.format(new Date())+"','add','"+data_source+"');";
						connection.execute(sql);
						break;
					case "2"://更新 update
						sql = "INSERT INTO  SG_SYS_NOTICE (NOTICE_DESC,TABLE_ID,START_TIME,OPT,DATA_SOURCE) VALUES('"+jsonObject.toString()+"','"+type+"','"+sdf.format(new Date())+"','update','"+data_source+"');";
						connection.execute(sql);
						break;
					case "3"://退运 back
						sql = "INSERT INTO  SG_SYS_NOTICE (NOTICE_DESC,TABLE_ID,START_TIME,OPT,DATA_SOURCE) VALUES('"+jsonObject.toString()+"','"+type+"','"+sdf.format(new Date())+"','back','"+data_source+"');";
						connection.execute(sql);
						break;
					case "4"://通知 notice
						sql = "INSERT INTO  SG_SYS_NOTICE (NOTICE_DESC,TABLE_ID,START_TIME,OPT,DATA_SOURCE) VALUES('"+jsonObject.toString()+"','"+type+"','"+sdf.format(new Date())+"','notice','"+data_source+"');";
						connection.execute(sql);
						break;
					default:
						break;
					}
				}
		    }
		}
	}
}
