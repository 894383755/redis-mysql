package com.kd.xxhyf.entity.ompse;


public class ConfTopic {

  private String id;
  private long flag;
  private String userid;
  private String topic;
  private long partition;
  private String recvid;
  private String agent;
  private String distcode;
  private String name;
  private String desc;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public long getFlag() {
    return flag;
  }

  public void setFlag(long flag) {
    this.flag = flag;
  }


  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }


  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }


  public long getPartition() {
    return partition;
  }

  public void setPartition(long partition) {
    this.partition = partition;
  }


  public String getRecvid() {
    return recvid;
  }

  public void setRecvid(String recvid) {
    this.recvid = recvid;
  }


  public String getAgent() {
    return agent;
  }

  public void setAgent(String agent) {
    this.agent = agent;
  }


  public String getDistcode() {
    return distcode;
  }

  public void setDistcode(String distcode) {
    this.distcode = distcode;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
