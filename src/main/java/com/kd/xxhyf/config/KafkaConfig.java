package com.kd.xxhyf.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class KafkaConfig {
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
    @Bean
    public com.kd.xxhyf.static_model.entity.ComsumerEntiy getComsumerEntiyStaticModel(){
        com.kd.xxhyf.static_model.entity.ComsumerEntiy comsumerEntiy = new com.kd.xxhyf.static_model.entity.ComsumerEntiy();
        comsumerEntiy.setKafkaservice(kafkaservice);
        comsumerEntiy.setGroup_id(groupIdStaticModel);
        comsumerEntiy.setEnable_auto_commit(enable_auto_commit);
        comsumerEntiy.setMax_poll_records(max_poll_records);
        comsumerEntiy.setQueue_length(queue_length);
        comsumerEntiy.setEarliest(earliest);
        comsumerEntiy.setTopic(topicSynchro);
        return comsumerEntiy;
    }

    @Bean
    public com.kd.xxhyf.synchro.entity.ComsumerEntiy getComsumerEntiySynchro(){
        com.kd.xxhyf.synchro.entity.ComsumerEntiy comsumerEntiy = new com.kd.xxhyf.synchro.entity.ComsumerEntiy();
        comsumerEntiy.setKafkaservice(kafkaservice);
        comsumerEntiy.setGroup_id_t(groupIdSynchro);
        comsumerEntiy.setEnable_auto_commit(enable_auto_commit);
        comsumerEntiy.setMax_poll_records(max_poll_records);
        comsumerEntiy.setQueue_length(queue_length);
        comsumerEntiy.setEarliest(earliest);
        comsumerEntiy.setTopic(topicSynchro);
        return comsumerEntiy;
    }

    @Bean(name = "comsumerEntiySynchroData")
    public com.kd.xxhyf.synchro_data.entity.ComsumerEntiy getComsumerEntiySynchroData(){
        com.kd.xxhyf.synchro_data.entity.ComsumerEntiy comsumerEntiy = new com.kd.xxhyf.synchro_data.entity.ComsumerEntiy();
        comsumerEntiy.setKafkaservice(kafkaservice);
        comsumerEntiy.setGroup_id_d(groupIdSynchroData);
        comsumerEntiy.setEnable_auto_commit(enable_auto_commit);
        comsumerEntiy.setMax_poll_records(max_poll_records);
        comsumerEntiy.setQueue_length(queue_length);
        comsumerEntiy.setEarliest(earliest);
        comsumerEntiy.setTopic(topicSynchroData);
        return comsumerEntiy;
    }

    @Bean
    public com.kd.xxhyf.notice.entity.ComsumerEntiy getComsumerEntiyNotice(){
        com.kd.xxhyf.notice.entity.ComsumerEntiy comsumerEntiy = new com.kd.xxhyf.notice.entity.ComsumerEntiy();
        comsumerEntiy.setKafkaservice(kafkaservice);
        comsumerEntiy.setGroup_id(groupIdNotice);
        comsumerEntiy.setEnable_auto_commit(enable_auto_commit);
        comsumerEntiy.setMax_poll_records(max_poll_records);
        comsumerEntiy.setQueue_length(queue_length);
        comsumerEntiy.setEarliest(earliest);
        comsumerEntiy.setTopic(topicNotice);
        return comsumerEntiy;
    }

}
