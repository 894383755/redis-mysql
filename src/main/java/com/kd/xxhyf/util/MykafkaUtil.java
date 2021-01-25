package com.kd.xxhyf.util;

import com.kd.kafkautill.service.DmqConsumer;
import com.kd.kafkautill.utill.DmqFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

@Component
@Slf4j
public class MykafkaUtil {

    @Value("${kafka.kafkaservice}")
    private String kafkaservice;
    @Value("${kafka.enable_auto_commit}")
    private String enable_auto_commit;
    @Value("${kafka.max_poll_records}")
    private int max_poll_records;
    @Value("${kafka.queue_length}")
    private int queue_length;
    @Value("${kafka.earliest}")
    private String earliest;
    @Value("${kafka.topicSynchroData}")
    private String topicSynchroData;
    @Value("${kafka.groupIdSynchroData}")
    private String groupIdSynchroData;
    @Value("${kafka.topicStaticModel}")
    private String topicStaticModel;
    @Value("${kafka.groupIdStaticModel}")
    private String groupIdStaticModel;
    @Value("${kafka.topicSynchro}")
    private String topicSynchro;
    @Value("${kafka.groupIdSynchro}")
    private String groupIdSynchro;
    @Value("${kafka.topicNotice}")
    private String topicNotice;
    @Value("${kafka.groupIdNotice}")
    private String groupIdNotice;


    public DmqConsumer getSynchroConsumer(){
        Properties props = getDefaultProperties();
        props.put("group.id", groupIdSynchro);
        DmqConsumer consumer = DmqFactory.createConsumer(props);
        consumer.subscribe(Arrays.asList(topicSynchro));
        return consumer;
    }

    public DmqConsumer getSynchroDataConsumer(){
        Properties props = getDefaultProperties();
        props.put("group.id", groupIdSynchroData);
        DmqConsumer consumer = DmqFactory.createConsumer(props);
        consumer.subscribe(Arrays.asList(topicSynchroData));
        return consumer;
    }

    public DmqConsumer getNoticeConsumer(){
        Properties props = getDefaultProperties();
        props.put("group.id", groupIdNotice);
        DmqConsumer consumer = DmqFactory.createConsumer(props);
        consumer.subscribe(Arrays.asList(topicNotice));
        return consumer;
    }

    public DmqConsumer getStaticModelConsumer(){
        Properties props = getDefaultProperties();
        props.put("group.id", groupIdStaticModel);
        DmqConsumer consumer = DmqFactory.createConsumer(props);
        consumer.subscribe(Arrays.asList(topicStaticModel));
        return consumer;
    }

    private Properties getDefaultProperties(){
        Properties props = new Properties();
        /* 定义kakfa 服务的地址，不需要将所有broker指定上 */
        props.put("bootstrap.servers", kafkaservice);
        // 相当于ConsumerID
        props.put("enable.auto.commit", enable_auto_commit);
        // 设置一次拉取多少条消息
        props.put("max.poll.records", max_poll_records);
        // key的序列化类
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        // value的序列化类
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        // t同组消费者（消费者二）将会从头开始消费Topic下的消息 latest,earliest
        //props.put("auto.offset.reset", comsumerEntiy.getEarliest());
        return props;
    }
}
