package com.kd.xxhyf.entity.ompse;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class SysTableinfo {

  private String id;
  private String chnTablename;
  private String enTablename;
  @TableField("'desc'")
  private String desc;
  private String businessId;
  private String isWhole;




}
