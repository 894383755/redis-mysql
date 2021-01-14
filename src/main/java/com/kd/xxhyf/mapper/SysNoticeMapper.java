package com.kd.xxhyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kd.xxhyf.entity.ompse.SysNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    @Select("SELECT * FROM SG_SYS_NOTICE WHERE NOTICE_DESC='${noticeDesc}' AND TABLE_ID='${tableId}'")
    List<SysNotice> selectListByNoticeDescAndTableId(String toString, String type);
}
