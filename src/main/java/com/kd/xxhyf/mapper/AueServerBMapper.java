package com.kd.xxhyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kd.xxhyf.entity.ompse.AueServerB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AueServerBMapper extends BaseMapper<AueServerB> {
    @Select("SELECT  *  FROM AUE_SERVER_B WHERE NAME IS NOT NULL")
    public List<AueServerB> selectListWhereNameNotNull();
}
