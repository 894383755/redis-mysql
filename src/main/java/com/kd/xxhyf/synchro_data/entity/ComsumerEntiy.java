package com.kd.xxhyf.synchro_data.entity;

/**
 * @author 吴林豪 E-mail:807005278@163.com
 *
 * 2019年10月30日 下午2:28:36
 *
 */
public class ComsumerEntiy {
	
	/**
	 * kafka服务IP
	 */
	private String kafkaservice;
	
	/**
	 * 消费者ID
	 */
	private String group_id_d;
	
	/**
	 * 同一个消费者重启之后不会重复消费之前消费过的消息
	 */
	private String enable_auto_commit;
	
	/**
	 * 同组消费者（消费者二）将会从头开始消费Topic下的消息 latest,earliest
	 */
	private String earliest;
	
	/**
	 * 设置一次拉取多少条消息
	 */
	private int max_poll_records;
	
	/**
	 * 队列长度
	 */
	private int queue_length;
	
	/**
	 * 消费者topic
	 */
	private String topic;

	
	public String getKafkaservice() {
		return kafkaservice;
	}

	public void setKafkaservice(String kafkaservice) {
		this.kafkaservice = kafkaservice;
	}
	
	public String getGroup_id_d() {
		return group_id_d;
	}

	public void setGroup_id_d(String group_id_d) {
		this.group_id_d = group_id_d;
	}

	public String getEnable_auto_commit() {
		return enable_auto_commit;
	}

	public void setEnable_auto_commit(String enable_auto_commit) {
		this.enable_auto_commit = enable_auto_commit;
	}

	public String getEarliest() {
		return earliest;
	}

	public void setEarliest(String earliest) {
		this.earliest = earliest;
	}

	public int getMax_poll_records() {
		return max_poll_records;
	}

	public void setMax_poll_records(int max_poll_records) {
		this.max_poll_records = max_poll_records;
	}

	public int getQueue_length() {
		return queue_length;
	}

	public void setQueue_length(int queue_length) {
		this.queue_length = queue_length;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
