package com.kd.xxhyf.entity.ompse;


public class OrgStatusWarnlevel {

  private long id;
  private long code;
  private String name;
  private String notes;
  private long sortedid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


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


  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }


  public long getSortedid() {
    return sortedid;
  }

  public void setSortedid(long sortedid) {
    this.sortedid = sortedid;
  }

}
