package com.kd.xxhyf.entity.ompse;


public class HisServiceUse {

  private String name;
  private String addr;
  private java.sql.Timestamp timepoint;
  private String method;
  private long stream;
  private long usetimes;
  private String avgcost;
  private String ip;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }


  public java.sql.Timestamp getTimepoint() {
    return timepoint;
  }

  public void setTimepoint(java.sql.Timestamp timepoint) {
    this.timepoint = timepoint;
  }


  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }


  public long getStream() {
    return stream;
  }

  public void setStream(long stream) {
    this.stream = stream;
  }


  public long getUsetimes() {
    return usetimes;
  }

  public void setUsetimes(long usetimes) {
    this.usetimes = usetimes;
  }


  public String getAvgcost() {
    return avgcost;
  }

  public void setAvgcost(String avgcost) {
    this.avgcost = avgcost;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

}
