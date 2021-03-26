package com.kd.xxhyf.notice;

import java.util.Properties;

import com.kd.xxhyf.util.MykafkaUtil;
import lombok.extern.slf4j.Slf4j;
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
import com.kd.xxhyf.notice.core.NoticeImpl;

/**
 * 处理 待办任务
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月21日 上午10:15:59
 *
 */

@Component
@Slf4j
public class NoticeTask {

	@Autowired
	private NoticeImpl noticeImpl;

	@Autowired
	private MykafkaUtil mykafkaUtil;

	@Async
	@Scheduled(fixedDelay = 20000)
	public void run (){
		try {
			DmqConsumer consumer = mykafkaUtil.getSynchroDataConsumer();
			log.info("待办任务服务kafka注册成功");
			while (true) {
				ConsumerRecords<String, String> records = consumer.receive();
				log.info("修改Notice目前已接收："+records.count()+"条数据");
				for (ConsumerRecord<String, String> record : records) {
					log.debug("待办收到的消息："+record.value());
					noticeImpl.run(record.value());
				}
				consumer.commitSync();
			}
		} catch (Exception e) {
			log.warn("待办任务消费者出现异常信息",e);
		}
	}

}
