package com.kd.xxhyf.synchro_data;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kd.xxhyf.synchro_data.core.SynchroDataServiceImpl;
import com.kd.xxhyf.synchro_data.entity.ComsumerEntiy;

/**
 * 
 * 同步试图 静态 模型数据 和 数据库上数数据
 * 
 * @author 吴林豪 E-mail:807005278@163.com
 *
 * 2019年10月30日 下午2:28:36
 *
 */
@Component
public class SynchroData {

	private static final Logger LOGGER =  LoggerFactory.getLogger(SynchroData.class);
	
	@Autowired
	private SynchroDataServiceImpl synchroDataServiceImpl;

	@Autowired
	private ComsumerEntiy comsumerEntiy_d;

	private KafkaConsumer<String, String> consumer = null;

	@Async
	@Scheduled(fixedDelay = 20000)
	public void run (){
		try {
			Properties props = new Properties();
			/* 定义kakfa 服务的地址，不需要将所有broker指定上 */
			props.put("bootstrap.servers", comsumerEntiy_d.getKafkaservice());
			// 相当于ConsumerID
			//System.err.println("CID:" + comsumerEntiy.getGroup_id_d());
			props.put("group.id", comsumerEntiy_d.getGroup_id_d());
			// 如果true,consumer定期地往zookeeper写入每个分区的offset
			/* 是否自动确认offset */
			// 同一个消费者重启之后不会重复消费之前消费过的消息
			props.put("enable.auto.commit", comsumerEntiy_d.getEnable_auto_commit());
			// 设置一次拉取多少条消息
			props.put("max.poll.records", comsumerEntiy_d.getMax_poll_records());
			// key的序列化类 
			props.put("key.deserializer",
					"org.apache.kafka.common.serialization.StringDeserializer");
			// value的序列化类 
			props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
			// t同组消费者（消费者二）将会从头开始消费Topic下的消息 latest,earliest
			//props.put("auto.offset.reset", comsumerEntiy.getEarliest());
			//consumer = DmqFactory.createConsumer(props);
			KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
			consumer.subscribe(Arrays.asList(comsumerEntiy_d.getTopic()));
			//consumer.subscribe(comsumerEntiy_t.getTopic());
			int count = 0;
			System.err.println("codis同步--------------------服务kafka注册成功");
			LOGGER.info("codis同步服务kafka注册成功");
			while (true) {
				// 从Broker拉取消息,拉取超时时间设置为100ms
			//	KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
			//	recive(consumer, comsumerEntiy.getTopic());
			//	 ConsumerRecords<String, String> records = consumer.receive();
				ConsumerRecords<String, String> records = consumer.poll(100);
				
				for (ConsumerRecord<String, String> record : records) {
					// record.timestamp();
					 //System.out.println(records.count()+"---------开始接收");
					System.err.println("-------"+record.value());
					synchroDataServiceImpl.run(record.value());
					count++;
					LOGGER.info("修改Codis目前已接收："+count+"条数据");
					
				}
				// 提交的是这一批的records的offset的最后一个
				consumer.commitSync();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			consumer = null;
			LOGGER.warn("codis同步消费者出现异常信息",e);
		}
	}

}
