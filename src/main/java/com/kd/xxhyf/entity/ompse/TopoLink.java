package com.kd.xxhyf.entity.ompse;


public class TopoLink {

  private String id;
  private String startNode;
  private String endNode;
  private String breakpoints;
  private String linkname;
  private String functionPage;
  private String dccId;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getStartNode() {
    return startNode;
  }

  public void setStartNode(String startNode) {
    this.startNode = startNode;
  }


  public String getEndNode() {
    return endNode;
  }

  public void setEndNode(String endNode) {
    this.endNode = endNode;
  }


  public String getBreakpoints() {
    return breakpoints;
  }

  public void setBreakpoints(String breakpoints) {
    this.breakpoints = breakpoints;
  }


  public String getLinkname() {
    return linkname;
  }

  public void setLinkname(String linkname) {
    this.linkname = linkname;
  }


  public String getFunctionPage() {
    return functionPage;
  }

  public void setFunctionPage(String functionPage) {
    this.functionPage = functionPage;
  }


  public String getDccId() {
    return dccId;
  }

  public void setDccId(String dccId) {
    this.dccId = dccId;
  }

}
