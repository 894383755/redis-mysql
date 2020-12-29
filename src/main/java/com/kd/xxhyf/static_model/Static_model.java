package com.kd.xxhyf.static_model;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kd.kafkautill.service.DmqConsumer;
import com.kd.kafkautill.utill.DmqFactory;
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
	private StaticServiceImpl staticServiceImpl;
	
	private DmqConsumer consumer = null;

	@Scheduled(fixedDelay = 20000)
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
				ConsumerRecords<String, String> records = consumer.receive();
			
					for (ConsumerRecord<String, String> record : records) {

						String value=record.value()+"";
						try {
								LOGGER.debug(value);
								staticServiceImpl.run(record.value());
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
	
}
