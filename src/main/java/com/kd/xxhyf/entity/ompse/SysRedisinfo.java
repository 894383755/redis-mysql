package com.kd.xxhyf.entity.ompse;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

public class SysRedisinfo {

  private String id;
  private String pid;
  private String mysql;
  @TableField("'key'")
  private String key;
  private String hkey;
  private String hvalue;
  private String descp;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getMysql() {
    return mysql;
  }

  public void setMysql(String mysql) {
    this.mysql = mysql;
  }


  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }


  public String getHkey() {
    return hkey;
  }

  public void setHkey(String hkey) {
    this.hkey = hkey;
  }


  public String getHvalue() {
    return hvalue;
  }

  public void setHvalue(String hvalue) {
    this.hvalue = hvalue;
  }


  public String getDescp() {
    return descp;
  }

  public void setDescp(String descp) {
    this.descp = descp;
  }

}
