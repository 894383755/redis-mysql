package com.kd.xxhyf.entity.ompse;


public class AlarmJudgeB {

  private String id;
  private String deviceId;
  private String definitionId;
  private String tableName;
  private String alarmFiled;
  private double alarmCondition;
  private double delayTime;
  private String itemid;
  private String alarmEnumeration;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }


  public String getDefinitionId() {
    return definitionId;
  }

  public void setDefinitionId(String definitionId) {
    this.definitionId = definitionId;
  }


  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }


  public String getAlarmFiled() {
    return alarmFiled;
  }

  public void setAlarmFiled(String alarmFiled) {
    this.alarmFiled = alarmFiled;
  }


  public double getAlarmCondition() {
    return alarmCondition;
  }

  public void setAlarmCondition(double alarmCondition) {
    this.alarmCondition = alarmCondition;
  }


  public double getDelayTime() {
    return delayTime;
  }

  public void setDelayTime(double delayTime) {
    this.delayTime = delayTime;
  }


  public String getItemid() {
    return itemid;
  }

  public void setItemid(String itemid) {
    this.itemid = itemid;
  }


  public String getAlarmEnumeration() {
    return alarmEnumeration;
  }

  public void setAlarmEnumeration(String alarmEnumeration) {
    this.alarmEnumeration = alarmEnumeration;
  }

}
