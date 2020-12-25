package com.kd.xxhyf.entity.ompse;


public class AlarmTransferConfig {

  private String warninglevel;
  private String monitorobjecttype;
  private String logtype;
  private String logsubtype;
  private String alarmItemId;
  private long ifSendRecoverAlarm;
  private String logtypeName;
  private String logsubtypeName;


  public String getWarninglevel() {
    return warninglevel;
  }

  public void setWarninglevel(String warninglevel) {
    this.warninglevel = warninglevel;
  }


  public String getMonitorobjecttype() {
    return monitorobjecttype;
  }

  public void setMonitorobjecttype(String monitorobjecttype) {
    this.monitorobjecttype = monitorobjecttype;
  }


  public String getLogtype() {
    return logtype;
  }

  public void setLogtype(String logtype) {
    this.logtype = logtype;
  }


  public String getLogsubtype() {
    return logsubtype;
  }

  public void setLogsubtype(String logsubtype) {
    this.logsubtype = logsubtype;
  }


  public String getAlarmItemId() {
    return alarmItemId;
  }

  public void setAlarmItemId(String alarmItemId) {
    this.alarmItemId = alarmItemId;
  }


  public long getIfSendRecoverAlarm() {
    return ifSendRecoverAlarm;
  }

  public void setIfSendRecoverAlarm(long ifSendRecoverAlarm) {
    this.ifSendRecoverAlarm = ifSendRecoverAlarm;
  }


  public String getLogtypeName() {
    return logtypeName;
  }

  public void setLogtypeName(String logtypeName) {
    this.logtypeName = logtypeName;
  }


  public String getLogsubtypeName() {
    return logsubtypeName;
  }

  public void setLogsubtypeName(String logsubtypeName) {
    this.logsubtypeName = logsubtypeName;
  }

}
