package com.kd.xxhyf.notice;

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
import com.kd.xxhyf.notice.core.NoticeImpl;
import com.kd.xxhyf.notice.entity.ComsumerEntiy;

/**
 * 处理 待办任务
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月21日 上午10:15:59
 *
 */

@Component
public class Notice {

	private static final Logger LOGGER =  LoggerFactory.getLogger(Notice.class);
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor1;
	
	@Autowired
	private RedisConfig redisConfig;
	
	@Autowired
	private Connection connection;
	
	@Autowired
	private ComsumerEntiy comsumerEntiy_n;
	
	private DmqConsumer consumer = null;
	
	@Async
	public void start(){
		this.run();
		while (true) {
			try {
				LOGGER.info("待办任务周期性监控消费者");
				this.monitor();
				Thread.sleep(60*1000);
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("待办任务监控异常",e);
			}
		}
	}
	
	public void run (){
		try {
			Properties props = new Properties();
			/* 定义kakfa 服务的地址，不需要将所有broker指定上 */
			props.put("bootstrap.servers", comsumerEntiy_n.getKafkaservice());
			// 相当于ConsumerID
			//System.err.println("CID:" + comsumerEntiy.getGroup_id_t());
			props.put("group.id", comsumerEntiy_n.getGroup_id());
			// 如果true,consumer定期地往zookeeper写入每个分区的offset
			/* 是否自动确认offset */
			// 同一个消费者重启之后不会重复消费之前消费过的消息
			/*props.put("enable.auto.commit", comsumerEntiy_t.getEnable_auto_commit());
			// 设置一次拉取多少条消息
			props.put("max.poll.records", comsumerEntiy_t.getMax_poll_records());
			 key的序列化类 
			props.put("key.deserializer",
					"org.apache.kafka.common.serialization.StringDeserializer");
			 value的序列化类 
			props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");*/
			// t同组消费者（消费者二）将会从头开始消费Topic下的消息 latest,earliest
			//props.put("auto.offset.reset", comsumerEntiy.getEarliest());
			consumer = DmqFactory.createConsumer(props);
			//KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
			//consumer.subscribe(Arrays.asList(comsumerEntiy_t.getTopic()));
			consumer.subscribe(comsumerEntiy_n.getTopic());
			int count = 0;
			LOGGER.info("待办任务服务kafka注册成功");
			while (true) {
				// 从Broker拉取消息,拉取超时时间设置为100ms
				ConsumerRecords<String, String> records = consumer.receive();
				//ConsumerRecords<String, String> records = consumer.poll(100);
				
				for (ConsumerRecord<String, String> record : records) {
					// record.timestamp();
					 //System.out.println(records.count()+"---------开始接收");
					LOGGER.debug("待办收到的消息："+record.value());
					taskExecutor1.execute(new NoticeImpl(record.value(), connection));
					count++;
					LOGGER.info("修改Notice目前已接收："+count+"条数据");
				}
				// 提交的是这一批的records的offset的最后一个
				consumer.commitSync();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			consumer = null;
			LOGGER.warn("待办任务消费者出现异常信息",e);
		}
	}
	
	/**
	 * 监控Kafka注册失败后再次重新注册
	 */
	public void monitor(){
		if(null==consumer){
			LOGGER.error("待办任务重新注册");
			this.run();
		}
	}

}
