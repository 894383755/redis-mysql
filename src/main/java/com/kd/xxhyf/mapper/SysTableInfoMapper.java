package com.kd.xxhyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kd.xxhyf.entity.ompse.SysTableinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SysTableInfoMapper extends BaseMapper<SysTableinfo> {
    @Select("SELECT * FROM OMPSE.SYS_TABLEINFO")
    public List<SysTableinfo> selectAll();
}
