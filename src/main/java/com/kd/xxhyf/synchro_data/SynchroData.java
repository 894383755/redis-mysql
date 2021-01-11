package com.kd.xxhyf.synchro_data;

import java.util.Arrays;
import java.util.Properties;

import com.kd.kafkautill.service.DmqConsumer;
import com.kd.xxhyf.util.MykafkaUtil;
import lombok.extern.slf4j.Slf4j;
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

/**
 * 
 * 同步试图 静态 模型数据 和 数据库上数数据
 *
 */
@Component
@Slf4j
public class SynchroData {
	@Autowired
	private SynchroDataServiceImpl synchroDataServiceImpl;
	@Autowired
	private MykafkaUtil mykafkaUtil;

	@Scheduled(fixedDelay = 20000)
	public void run (){
		try {
			DmqConsumer consumer = mykafkaUtil.getSynchroDataConsumer();
			log.info("codis同步服务kafka注册成功");
			while (true) {
			ConsumerRecords<String, String> records = consumer.receive();
				log.info("修改Codis目前已接收："+records.count()+"条数据");
				for (ConsumerRecord<String, String> record : records) {
					synchroDataServiceImpl.run(record.value());
				}
				consumer.commitSync();
			}
		} catch (Exception e) {
			log.error("codis同步消费者出现异常信息",e);
		}
	}

}
