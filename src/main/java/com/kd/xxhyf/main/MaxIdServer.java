package com.kd.xxhyf.main;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.kd.xxhyf.annotation.EnableAspectAnnotation;
import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.mapper.CommonTableMapper;
import com.kd.xxhyf.mapper.SysTableInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCommands;


/**
 * 初始化
 * @author 王俊磊 2021/1/6
 *
 */

@Component
@Slf4j
@ConditionalOnProperty(name="config.implementType",havingValue = "xinan")
@Primary
public class MaxIdServer {

	@Resource
	private SysTableInfoMapper sysTableInfoMapper;

	@Resource
	private CommonTableMapper commonTableMapper;

	@Value("${config.rediskey}")
	private String REDISKEY;

	@Qualifier("getJedisCommands")
	@Autowired
	private JedisCommands jedis;

	/**
	 * 初始化codis中存储的静态模型的最大ID,用于静态模型入库时获取ID使用，避免多线程导致ID重复问题
	 */
	@PostConstruct
	@EnableAspectAnnotation
	public void init(){
		log.info("开始执行初始化");
		try  {
			List<SysTableinfo> sysTableinfos = sysTableInfoMapper.selectListWhereStaticTable();
			for (SysTableinfo sysTableinfo : sysTableinfos) {
				String id = sysTableinfo.getId();
				try {
					Integer maxId = commonTableMapper.selectMaxSerialIdWhereTableName(sysTableinfo.getEnTablename());
					if(maxId != null){
						jedis.set(REDISKEY+id+"_num", maxId.toString());
					}else{
						jedis.set(REDISKEY+id+"_num", "0");
					}
				} catch (Exception e) {
					log.error("获取其异常： selectMaxSerialIdWhereTableName("+sysTableinfo.getEnTablename());
					throw e;
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("结束执行初始化");
	}
}
