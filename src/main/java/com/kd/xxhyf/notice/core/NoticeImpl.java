package com.kd.xxhyf.notice.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.kd.xxhyf.entity.ompse.SysNotice;
import com.kd.xxhyf.mapper.SysNoticeMapper;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.kd.xxhyf.util.Connection;
import com.kd.xxhyf.util.XmlUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 处理 待办任务service
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月20日 下午4:58:31
 *
 */
@Component
@ConditionalOnProperty(name="config.implementType",havingValue = "xinan")
@Primary
public class NoticeImpl implements Notice {

	@Autowired
	private Connection connection;

	@Resource
	private SysNoticeMapper sysNoticeMapper;

	@Async
	@Override
	public void run(String receiveString) {//线程启动方法
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
		
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//确定获取的当前时间是北京、上海时间
		
		Map<String, List<Map<String, Object>>> map = XmlUtil.statusXml(receiveString);//解析XML
		
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

				List<SysNotice> sysNotices = sysNoticeMapper.selectListByNoticeDescAndTableId(jsonObject.toString(), type);

				if(sysNotices.isEmpty()){//只有在数据库中不存在时再执行
					String opt = null;
					switch (oper_type) {
					case "1"://新增 add
						opt = "add";
						break;
					case "2"://更新 update
						opt = "update";
						break;
					case "3"://退运 back
						opt = "back";
						break;
					case "4"://通知 notice
						opt = "notice";
						break;
					default:
						break;
					}
					SysNotice sysNotice = new SysNotice();
					sysNotice.setNoticeDesc(jsonObject.toString());
					sysNotice.setDataType(Long.getLong(type));
					sysNotice.setTableId(type);
					sysNotice.setStartTime(sdf.format(new Date()));
					sysNotice.setOpt(opt);
					sysNotice.setDataSource(Long.getLong(data_source));
				}
		    }
		}
	}
}
