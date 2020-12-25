package com.kd.xxhyf.entity.ompse;


public class ConfWarningdefecttaskTable {

  private String taskid;
  private java.sql.Timestamp stime;
  private java.sql.Timestamp etime;
  private String isuse;
  private String describe;
  private String platformname;
  private java.sql.Timestamp taskcreattime;
  private String taskcreatuser;


  public String getTaskid() {
    return taskid;
  }

  public void setTaskid(String taskid) {
    this.taskid = taskid;
  }


  public java.sql.Timestamp getStime() {
    return stime;
  }

  public void setStime(java.sql.Timestamp stime) {
    this.stime = stime;
  }


  public java.sql.Timestamp getEtime() {
    return etime;
  }

  public void setEtime(java.sql.Timestamp etime) {
    this.etime = etime;
  }


  public String getIsuse() {
    return isuse;
  }

  public void setIsuse(String isuse) {
    this.isuse = isuse;
  }


  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }


  public String getPlatformname() {
    return platformname;
  }

  public void setPlatformname(String platformname) {
    this.platformname = platformname;
  }


  public java.sql.Timestamp getTaskcreattime() {
    return taskcreattime;
  }

  public void setTaskcreattime(java.sql.Timestamp taskcreattime) {
    this.taskcreattime = taskcreattime;
  }


  public String getTaskcreatuser() {
    return taskcreatuser;
  }

  public void setTaskcreatuser(String taskcreatuser) {
    this.taskcreatuser = taskcreatuser;
  }

}
