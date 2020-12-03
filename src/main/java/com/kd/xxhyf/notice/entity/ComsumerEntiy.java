package com.kd.xxhyf.notice.entity;

/**
 * 
 * @author 刘景涛 E-mail:18615564548@163.com
 *
 * 2018年9月11日 下午2:47:13
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
	private String group_id;

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

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
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
