package com.kd.xxhyf.synchro;

import java.util.Arrays;
import java.util.Properties;

import com.kd.kafkautill.service.DmqConsumer;
import com.kd.kafkautill.utill.DmqFactory;
import com.kd.xxhyf.util.MykafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kd.xxhyf.synchro.core.SynchroServiceImpl;

/**
 * 
 * 同步关系库到实时库
 *
 */
@Component
public class Synchro {

	private static final Logger LOGGER =  LoggerFactory.getLogger(Synchro.class);
	
	@Autowired
	private SynchroServiceImpl synchroServiceImpl;
	
	@Autowired
	private MykafkaUtil mykafkaUtil;


	@Scheduled(fixedDelay = 20000)
	public void run (){
		try {
			DmqConsumer consumer = mykafkaUtil.getSynchroConsumer();
			LOGGER.info("codis同步服务kafka注册成功");
			while (true) {
				ConsumerRecords<String, String> records = consumer.receive();
				if (records.count() == 0){
					continue;
				}
				LOGGER.info("修改Codis目前已接收："+records.count()+"条数据");
				for (ConsumerRecord<String, String> record : records) {
					synchroServiceImpl.run(record.value());
				}
				// 提交的是这一批的records的offset的最后一个
				consumer.commitSync();
			}
		} catch (Exception e) {
			LOGGER.error("codis同步消费者出现异常信息",e);
		}
	}
}
