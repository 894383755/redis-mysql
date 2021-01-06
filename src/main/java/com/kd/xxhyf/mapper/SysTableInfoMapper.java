package com.kd.xxhyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kd.xxhyf.entity.ompse.SysTableinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SysTableInfoMapper extends BaseMapper<SysTableinfo> {
    /**
     * 寻找静态表
     * @return
     */
    @Select("SELECT * FROM SYS_TABLEINFO WHERE ID LIKE '%0' AND EN_TABLENAME!='RUNNING_FILE_B'")
    public List<SysTableinfo> selectListWhereStaticTable();
}
