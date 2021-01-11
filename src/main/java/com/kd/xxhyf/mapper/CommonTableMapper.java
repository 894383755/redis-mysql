package com.kd.xxhyf.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommonTableMapper {
    @Select("SELECT MAX(SUBSTR(ID,11,8)) AS NUM FROM ${tablename}")
    public Integer selectMaxSerialIdWhereTableName(String tablename);

    //public Integer insertByTableNameAndId(String tableName,)
}
