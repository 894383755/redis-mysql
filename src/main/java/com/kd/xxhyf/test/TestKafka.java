package com.kd.xxhyf.test;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.kd.kafkautill.utill.DmqFactory;
import com.kd.xxhyf.static_model.core.StaticServiceImpl;
import com.kd.xxhyf.synchro.core.SynchroServiceImpl;

public class TestKafka {
	
   public static void main(String[] args) {
		try {
			Properties props = new Properties();
			/* 定义kakfa 服务的地址，不需要将所有broker指定上 */
			props.put("bootstrap.servers","192.100.1.30:9092,192.100.1.31:9092,192.100.1.32:9092");
			// 相当于ConsumerID
			//System.err.println("CID:" + comsumerEntiy.getGroup_id_t());
			props.put("group.id", "model_issue");
			// 如果true,consumer定期地往zookeeper写入每个分区的offset
			/* 是否自动确认offset */
			// 同一个消费者重启之后不会重复消费之前消费过的消息
			props.put("enable.auto.commit", true);
			// 设置一次拉取多少条消息
			props.put("max.poll.records",10);
			// key的序列化类 
			props.put("key.deserializer","org.apache.kafka.common.serialization.ByteArrayDeserializer");
			// value的序列化类 
			props.put("value.deserializer","org.apache.kafka.common.serialization.ByteArrayDeserializer");
			//props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
			// value的序列化类 
			//props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
			// t同组消费者（消费者二）将会从头开始消费Topic下的消息 latest,earliest
			//props.put("auto.offset.reset", comsumerEntiy.getEarliest());
			//consumer = DmqFactory.createConsumer(props);
			KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
			consumer.subscribe(Arrays.asList("topic_model_issue"));
			//consumer.subscribe(comsumerEntiy_t.getTopic());
			int count = 0;
			System.err.println("kafka注册成功");
	
			while (true) {
			
				ConsumerRecords<String, byte[]> records = consumer.poll(100);
				for (ConsumerRecord<String, byte[]> record : records) {
					// record.timestamp();
					 //System.out.println(records.count()+"---------开始接收");
					byte[] bs = record.value();
				
					   for(int k=0;k<bs.length;k++){
                           System.out.print(bs[k]+" ");
                       }
					
					count++;
				
					
				}
				// 提交的是这一批的records的offset的最后一个
				consumer.commitSync();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
	
}
}
