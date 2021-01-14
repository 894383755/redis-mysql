package com.kd.xxhyf.entity.ompse;


public class SysNotice {

  private long id;
  private String noticeDesc;
  private String tableId;
  private String startTime;
  private String desc;
  private String opt;
  private long dataType;
  private long dataSource;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getNoticeDesc() {
    return noticeDesc;
  }

  public void setNoticeDesc(String noticeDesc) {
    this.noticeDesc = noticeDesc;
  }


  public String getTableId() {
    return tableId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }


  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }


  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }


  public String getOpt() {
    return opt;
  }

  public void setOpt(String opt) {
    this.opt = opt;
  }


  public long getDataType() {
    return dataType;
  }

  public void setDataType(long dataType) {
    this.dataType = dataType;
  }


  public long getDataSource() {
    return dataSource;
  }

  public void setDataSource(long dataSource) {
    this.dataSource = dataSource;
  }

}
