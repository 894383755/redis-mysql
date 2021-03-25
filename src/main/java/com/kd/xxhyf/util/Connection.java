package com.kd.xxhyf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据源的sql执行方法
 * @author 刘景涛 E-mail:18615564548@163.com
 * 2018年9月11日 下午2:43:21
 * 修改 ： 吴林豪 E-mail:15200028709@163.com
 * 2019年10月15日 
 *
 */
@Component("connection")
@Transactional
@Slf4j
public class Connection {
	
	@Autowired
	private JdbcTemplate template;

	/**
	 * 数据源一的sql只执行返回结果集
	 * @param sql
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> findForDruid(String sql,Object ... obj){
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		try {
			sql = sql.replace("SG_SG_", "SG_");
			list2 = template.queryForList(sql);
		} catch (Exception e) {
			log.error("执行JDBCTemplate错误" + e.getMessage());
		}
		return list2;
	}

	/**
	 * 数据源一的 增删改
	 * 执行单条SQL
	 * @param sql
	 * @return
	 */
	public void execute(String sql){
		sql = sql.replace("SG_SG_", "SG_");
		try {
			template.execute(sql);
		} catch (Exception e) {
			log.error("执行JDBCTemplate错误: " + sql + "\n异常为: ",e.getMessage());
		}
	}
	
	/**
	 * 数据源一的 增删改 遍历执行
	 * 执行多条SQL
	 * @param sql
	 * @return
	 */
	public boolean execute(List<String> sql){
		boolean b = false;
		for (int i = 0; i < sql.size(); i++) {
			try {
				execute(sql.get(i));
			} catch (Exception e) {
				// TODO: handle exception
				log.warn(e.getMessage());
				b = false;
			}
		}
		
		return b;
	}
}
