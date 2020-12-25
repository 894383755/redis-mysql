package com.kd.xxhyf.entity.ompse;


public class WarningEncrate {

  private String indexid;
  private String id;
  private java.sql.Timestamp warningtime;
  private String warningtype;
  private String content;
  private String confirmstate;


  public String getIndexid() {
    return indexid;
  }

  public void setIndexid(String indexid) {
    this.indexid = indexid;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public java.sql.Timestamp getWarningtime() {
    return warningtime;
  }

  public void setWarningtime(java.sql.Timestamp warningtime) {
    this.warningtime = warningtime;
  }


  public String getWarningtype() {
    return warningtype;
  }

  public void setWarningtype(String warningtype) {
    this.warningtype = warningtype;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getConfirmstate() {
    return confirmstate;
  }

  public void setConfirmstate(String confirmstate) {
    this.confirmstate = confirmstate;
  }

}
