package com.kd.xxhyf.entity.ompse;


public class ConfAutstate {

  private long code;
  private String name;
  private String effectFlag;
  private java.sql.Timestamp createTime;
  private String note;
  private long id;


  public long getCode() {
    return code;
  }

  public void setCode(long code) {
    this.code = code;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getEffectFlag() {
    return effectFlag;
  }

  public void setEffectFlag(String effectFlag) {
    this.effectFlag = effectFlag;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
