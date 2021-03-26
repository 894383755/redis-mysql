package com.kd.xxhyf.mysql_redis.core;

import com.kd.xxhyf.entity.ompse.SysTableinfo;

public interface RedisToMysql {
    public void run(SysTableinfo sysTableinfo);

    void static_data(String paramTableId);

    void synNowRunDateNeedStaticDataToCodis();

    void syn_view();

    void runing_data();

    void server();

    void synDeviceId();
}
