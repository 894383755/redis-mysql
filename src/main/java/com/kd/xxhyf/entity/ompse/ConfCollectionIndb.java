package com.kd.xxhyf.entity.ompse;


public class ConfCollectionIndb {

  private long id;
  private String devtype;
  private long eventId;
  private long subEventId;
  private String tableId;
  private String descr;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getDevtype() {
    return devtype;
  }

  public void setDevtype(String devtype) {
    this.devtype = devtype;
  }


  public long getEventId() {
    return eventId;
  }

  public void setEventId(long eventId) {
    this.eventId = eventId;
  }


  public long getSubEventId() {
    return subEventId;
  }

  public void setSubEventId(long subEventId) {
    this.subEventId = subEventId;
  }


  public String getTableId() {
    return tableId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }


  public String getDescr() {
    return descr;
  }

  public void setDescr(String descr) {
    this.descr = descr;
  }

}
