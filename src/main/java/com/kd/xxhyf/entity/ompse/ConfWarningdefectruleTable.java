package com.kd.xxhyf.entity.ompse;


public class ConfWarningdefectruleTable {

  private String taskid;
  private String devtype;
  private String logtype;
  private String sublogtype;
  private String regex;
  private String regexdetail;
  private long ruleid;


  public String getTaskid() {
    return taskid;
  }

  public void setTaskid(String taskid) {
    this.taskid = taskid;
  }


  public String getDevtype() {
    return devtype;
  }

  public void setDevtype(String devtype) {
    this.devtype = devtype;
  }


  public String getLogtype() {
    return logtype;
  }

  public void setLogtype(String logtype) {
    this.logtype = logtype;
  }


  public String getSublogtype() {
    return sublogtype;
  }

  public void setSublogtype(String sublogtype) {
    this.sublogtype = sublogtype;
  }


  public String getRegex() {
    return regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }


  public String getRegexdetail() {
    return regexdetail;
  }

  public void setRegexdetail(String regexdetail) {
    this.regexdetail = regexdetail;
  }


  public long getRuleid() {
    return ruleid;
  }

  public void setRuleid(long ruleid) {
    this.ruleid = ruleid;
  }

}
