package com.kd.xxhyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kd.xxhyf.entity.ompse.SysFkfront;
import com.kd.xxhyf.entity.ompse.SysFkrelation;
import com.kd.xxhyf.entity.ompse.SysFkshow;
import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.entity.sysdba.SysFiledinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysFiledInfoMapper extends BaseMapper<SysFiledinfo> {
    @Select("SELECT *  FROM SYS_FILEDINFO  WHERE TABLE_ID  = '${tableId}'")
    public List<SysFiledinfo> selectListByTableId(String tableId);
}
