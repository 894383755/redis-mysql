package com.kd.xxhyf.entity.ompse;


public class ConfRegexFilter {

  private String ruleid;
  private String regex;
  private String saveflag;
  private java.sql.Timestamp invalidTime;
  private String usageFlag;
  private String remark;
  private String name;


  public String getRuleid() {
    return ruleid;
  }

  public void setRuleid(String ruleid) {
    this.ruleid = ruleid;
  }


  public String getRegex() {
    return regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }


  public String getSaveflag() {
    return saveflag;
  }

  public void setSaveflag(String saveflag) {
    this.saveflag = saveflag;
  }


  public java.sql.Timestamp getInvalidTime() {
    return invalidTime;
  }

  public void setInvalidTime(java.sql.Timestamp invalidTime) {
    this.invalidTime = invalidTime;
  }


  public String getUsageFlag() {
    return usageFlag;
  }

  public void setUsageFlag(String usageFlag) {
    this.usageFlag = usageFlag;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
