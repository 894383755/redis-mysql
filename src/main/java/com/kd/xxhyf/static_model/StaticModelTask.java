package com.kd.xxhyf.static_model;

import com.kd.xxhyf.util.MykafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kd.kafkautill.service.DmqConsumer;
import com.kd.xxhyf.static_model.core.StaticServiceImpl;

/**
 * 
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:47:24
 *
 */

@Component
public class StaticModelTask {

	private static final Logger LOGGER =  LoggerFactory.getLogger(StaticModelTask.class);

	@Autowired
	private MykafkaUtil mykafkaUtil;

	@Autowired
	private StaticServiceImpl staticServiceImpl;

	@Scheduled(fixedDelay = 20000)
	public void run(){
		try {
			LOGGER.info("静态数据开始运行");
			DmqConsumer consumer = mykafkaUtil.getStaticModelConsumer();
			LOGGER.info("静态数据服务kafka注册成功");
			while (true) {
				ConsumerRecords<String, String> records = consumer.receive();
				if (records.count() == 0){
					continue;
				}
				LOGGER.info("静态数据目前已接收："+records.count()+"条数据");
				for (ConsumerRecord<String, String> record : records) {
						String value=record.value()+"";
						try {
							LOGGER.debug(value);
							staticServiceImpl.run(record.value());
						} catch (Exception e) {
							LOGGER.error(value+"----",e);
						}
					}
				consumer.commitSync();//提交
			}
		} catch (Exception e) {
			LOGGER.warn("静态数据消费者出现异常信息",e);
		}
	}
	
}
