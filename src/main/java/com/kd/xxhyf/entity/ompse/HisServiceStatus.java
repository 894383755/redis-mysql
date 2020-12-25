package com.kd.xxhyf.entity.ompse;


public class HisServiceStatus {

  private String name;
  private String ip;
  private String addr;
  private java.sql.Timestamp timepoint;
  private long status;
  private String sysuser;
  private String pid;
  private String cpurate;
  private String memrate;
  private String memuse;
  private String vmuse;
  private java.sql.Timestamp starttime;
  private String version;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
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


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public String getSysuser() {
    return sysuser;
  }

  public void setSysuser(String sysuser) {
    this.sysuser = sysuser;
  }


  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getCpurate() {
    return cpurate;
  }

  public void setCpurate(String cpurate) {
    this.cpurate = cpurate;
  }


  public String getMemrate() {
    return memrate;
  }

  public void setMemrate(String memrate) {
    this.memrate = memrate;
  }


  public String getMemuse() {
    return memuse;
  }

  public void setMemuse(String memuse) {
    this.memuse = memuse;
  }


  public String getVmuse() {
    return vmuse;
  }

  public void setVmuse(String vmuse) {
    this.vmuse = vmuse;
  }


  public java.sql.Timestamp getStarttime() {
    return starttime;
  }

  public void setStarttime(java.sql.Timestamp starttime) {
    this.starttime = starttime;
  }


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

}
