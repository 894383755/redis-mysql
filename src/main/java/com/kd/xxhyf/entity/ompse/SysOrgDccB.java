package com.kd.xxhyf.entity.ompse;


public class SysOrgDccB {

  private String id;
  private String name;
  private String nameAbbreviation;
  private String parentId;
  private String omsDccId;
  private String mainMachineTopoId;
  private String courseTideTopoId;
  private long level;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getNameAbbreviation() {
    return nameAbbreviation;
  }

  public void setNameAbbreviation(String nameAbbreviation) {
    this.nameAbbreviation = nameAbbreviation;
  }


  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }


  public String getOmsDccId() {
    return omsDccId;
  }

  public void setOmsDccId(String omsDccId) {
    this.omsDccId = omsDccId;
  }


  public String getMainMachineTopoId() {
    return mainMachineTopoId;
  }

  public void setMainMachineTopoId(String mainMachineTopoId) {
    this.mainMachineTopoId = mainMachineTopoId;
  }


  public String getCourseTideTopoId() {
    return courseTideTopoId;
  }

  public void setCourseTideTopoId(String courseTideTopoId) {
    this.courseTideTopoId = courseTideTopoId;
  }


  public long getLevel() {
    return level;
  }

  public void setLevel(long level) {
    this.level = level;
  }

}
