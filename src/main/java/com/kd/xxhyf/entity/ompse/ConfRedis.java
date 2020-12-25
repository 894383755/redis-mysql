package com.kd.xxhyf.entity.ompse;


public class ConfRedis {

  private String confId;
  private String confValue;
  private String remark;
  private String name;
  private String display;
  private String sortid;
  private long encryption;
  private String ispasswd;


  public String getConfId() {
    return confId;
  }

  public void setConfId(String confId) {
    this.confId = confId;
  }


  public String getConfValue() {
    return confValue;
  }

  public void setConfValue(String confValue) {
    this.confValue = confValue;
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


  public String getDisplay() {
    return display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }


  public String getSortid() {
    return sortid;
  }

  public void setSortid(String sortid) {
    this.sortid = sortid;
  }


  public long getEncryption() {
    return encryption;
  }

  public void setEncryption(long encryption) {
    this.encryption = encryption;
  }


  public String getIspasswd() {
    return ispasswd;
  }

  public void setIspasswd(String ispasswd) {
    this.ispasswd = ispasswd;
  }

}
