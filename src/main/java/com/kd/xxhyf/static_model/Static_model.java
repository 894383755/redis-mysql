package com.kd.xxhyf.static_model;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import com.kd.kafkautill.service.DmqConsumer;
import com.kd.kafkautill.utill.DmqFactory;
import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.database.connection.Connection;
import com.kd.xxhyf.static_model.core.StaticServiceImpl;
import com.kd.xxhyf.static_model.entity.ComsumerEntiy;

/**
 * 
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:47:24
 *
 */

@Component
public class Static_model { 

	private static final Logger LOGGER =  LoggerFactory.getLogger(Static_model.class);
			
	@Autowired
	private ComsumerEntiy comsumerEntiy;
	
	@Autowired
	private RedisConfig redisConfig;
	
	@Autowired
	private Connection connection;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	private DmqConsumer consumer = null;
	
	@Async
	public void start(){
		this.run();
		while (true) {
			try {
				LOGGER.info("静态数据周期性监控消费者");
				this.monitor();
				Thread.sleep(60*1000);
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("静态数据监控异常",e);
			}
		}
		
	}
	
	public void run(){
		try {
			Properties props = new Properties();
			/* 定义kakfa 服务的地址，不需要将所有broker指定上 */
			props.put("bootstrap.servers", comsumerEntiy.getKafkaservice());
			// 相当于ConsumerID
			props.put("group.id", comsumerEntiy.getGroup_id());
			// 如果true,consumer定期地往zookeeper写入每个分区的offset
			/* 是否自动确认offset */
			// 同一个消费者重启之后不会重复消费之前消费过的消息
			/*props.put("enable.auto.commit", comsumerEntiy.getEnable_auto_commit());
			// 设置一次拉取多少条消息
			props.put("max.poll.records", comsumerEntiy.getMax_poll_records());
			 key的序列化类 
			props.put("key.deserializer",
					"org.apache.kafka.common.serialization.StringDeserializer");
			 value的序列化类 
			props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");*/
			// t同组消费者（消费者二）将会从头开始消费Topic下的消息 latest,earliest
			//props.put("auto.offset.reset", comsumerEntiy.getEarliest());
			consumer = DmqFactory.createConsumer(props);
			//KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
			//consumer.subscribe(Arrays.asList(comsumerEntiy.getTopic()));
			consumer.subscribe(comsumerEntiy.getTopic());
			int count = 0;
			LOGGER.info("静态数据服务kafka注册成功");
			while (true) {
				// 从Broker拉取消息,拉取超时时间设置为100ms
				Thread.sleep(5000);
				
//				String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>"
//						+ "<lists>"
//						+ "<list device=\"\" type=\"51150\">"
//								+ "<CONTEXT_ID>realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,sys,sys,sys,sys,sys,sys,sys,sys,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,sys,realtime,realtime,realtime,realtime,realtime,realtime,realtime</CONTEXT_ID>"
//								+ "<APP_ID>public,public,public,public,public,public,data_srv,data_srv,data_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,public,public,public,public,public,public,public,public,data_srv,data_srv,data_srv,data_srv,data_srv,data_srv,data_srv,base_srv,sms_scms,sms_scada,data_srv,public,public,sms_fes,sms_scada</APP_ID>"
//								+ "<PROCESS_ID>rtdb_srv,rtdb_pub,rtdb_sca,rtdbmodi,caseserver,down_daemon,down_srv,dbmodify,sqlsp_srv,sys_nicmonitor,procm,procm_mon,msg_bus,sys_srv,sys_alarm,remote_exed,sys_app,warnserver,priv_srv,locator,midmmi,middata,midbrow,evt_sender,evt_recv,model_modify,tri_rtdb_client,ftpserv,midhs,dbmonitor,db_commit,remote_alarm,sys_info,smsfilesrv,smsalarmserver,sync_srv,In_proxy_t,Out_proxy_t,smsdataserver,smsdmailrecv</PROCESS_ID>"
//								+ "<NODEID>710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007,710199010600000007</NODEID>"
//								+ "<time></time>"
//								+ "</list>"
//								+ "</lists>";
//				taskExecutor.execute(new StaticServiceImpl(xml,connection,redisConfig));

				ConsumerRecords<String, String> records = consumer.receive();
			
					for (ConsumerRecord<String, String> record : records) {

						String value=record.value()+"";
						try {
								LOGGER.debug(value);
								taskExecutor.execute(new StaticServiceImpl(record.value(),connection,redisConfig));
						} catch (Exception e) {
							LOGGER.error(value+"----",e);
						}
						count++;
						LOGGER.info("静态数据目前已接收："+count+"条数据");
					}
					consumer.commitSync();//提交
			}
		} catch (Exception e) {
			// TODO: handle exception
			consumer = null;
			LOGGER.warn("静态数据消费者出现异常信息",e);
		}
	}
	
	/**
	  * 监控Kafka注册失败后再次重新注册
	 */
	public void monitor (){
		if(null==consumer){
			LOGGER.error("静态数据重新注册");
			this.run();
		}
	}
	
}
