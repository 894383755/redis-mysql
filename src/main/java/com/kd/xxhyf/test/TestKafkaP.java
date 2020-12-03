package com.kd.xxhyf.test;

import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TestKafkaP {
	
   public static void main(String[] args) {
	
	        try {

	            Properties props = new Properties();
	            /* 定义kakfa 服务的地址，不需要将所有broker指定上 */
	            props.put("bootstrap.servers", "192.100.1.71:9092,192.100.1.74:9092,192.100.1.77:9092");
	        /*ack是判别请求是否成功发送了。指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。
	         0：不进行消息接收确认，即Client端发送完成后不会等待Broker的确认
	         1：由Leader确认，Leader接收到消息后会立即返回确认信息
	         all：集群完整确认，Leader会等待所有in-sync的follower节点都确认收到消息后，再返回确认信息*/
	            props.put("acks", "all");
	            /* retries，如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性 */
	            props.put("retries", 0);
	            props.put("batch.size", 16384);
	            props.put("PID","PID_fj_aue");
	            /* key的序列化类 */
	            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	            /* value的序列化类 */
	            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

	            /* key的序列化类 */
	            // props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	            /* value的序列化类 */
	            // props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	            //K代表每条消息的key类型，V代表消息类型。消息的key用于决定此条消息业务类型可有可无，
	            KafkaProducer<String,String> producer = new KafkaProducer<String,String>(props);
	            // DmqProducer producer=  DmqFactory.createProducer(props);
	            String data = "jsfksfjfjskfwefhfhsdlfhadfhasdkl;fhklawefhklrhfklsdh";
	          
	            ProducerRecord<String,String> record = new ProducerRecord<String,String>("fj_kafkatransfer_aue", data);
	            producer.send(record, new Callback() {
	                @Override
	                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
	                    e.printStackTrace();
	                    System.out.println("The offset of the record we just sent is: " + recordMetadata.offset());

	                }
	            });

	           // producer.close();

	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
	
	
}

