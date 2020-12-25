package com.kd.xxhyf.entity.ompse;


public class SysMenu {

  private String id;
  private String fathermenuid;
  private String menunameCh;
  private String menunameEn;
  private String menuurl;
  private String menuorder;
  private String relatedSvgfiles;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getFathermenuid() {
    return fathermenuid;
  }

  public void setFathermenuid(String fathermenuid) {
    this.fathermenuid = fathermenuid;
  }


  public String getMenunameCh() {
    return menunameCh;
  }

  public void setMenunameCh(String menunameCh) {
    this.menunameCh = menunameCh;
  }


  public String getMenunameEn() {
    return menunameEn;
  }

  public void setMenunameEn(String menunameEn) {
    this.menunameEn = menunameEn;
  }


  public String getMenuurl() {
    return menuurl;
  }

  public void setMenuurl(String menuurl) {
    this.menuurl = menuurl;
  }


  public String getMenuorder() {
    return menuorder;
  }

  public void setMenuorder(String menuorder) {
    this.menuorder = menuorder;
  }


  public String getRelatedSvgfiles() {
    return relatedSvgfiles;
  }

  public void setRelatedSvgfiles(String relatedSvgfiles) {
    this.relatedSvgfiles = relatedSvgfiles;
  }

}
