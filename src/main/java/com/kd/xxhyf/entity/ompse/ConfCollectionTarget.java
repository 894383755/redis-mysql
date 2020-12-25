package com.kd.xxhyf.entity.ompse;


public class ConfCollectionTarget {

  private long id;
  private String fieldname;
  private long phNo;
  private String tableId;
  private long alarmFlag;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getFieldname() {
    return fieldname;
  }

  public void setFieldname(String fieldname) {
    this.fieldname = fieldname;
  }


  public long getPhNo() {
    return phNo;
  }

  public void setPhNo(long phNo) {
    this.phNo = phNo;
  }


  public String getTableId() {
    return tableId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }


  public long getAlarmFlag() {
    return alarmFlag;
  }

  public void setAlarmFlag(long alarmFlag) {
    this.alarmFlag = alarmFlag;
  }

}
