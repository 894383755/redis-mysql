package com.kd.xxhyf.main;

import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.mapper.CommonTableMapper;
import com.kd.xxhyf.mapper.SysTableInfoMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest()
@RunWith(SpringRunner.class)
class RunTest {

    @Resource
    private SysTableInfoMapper sysTableInfoMapper;

    @Resource
    private CommonTableMapper commonTableMapper;

    @BeforeEach
    void setUp() {
        System.out.println(("----- RunTest class test ------"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void init() {
        System.out.println(("----- init method test ------"));
        List<SysTableinfo> userList = sysTableInfoMapper.selectList(null);
        Assert.assertNotEquals(0, userList.size());
        Integer id = commonTableMapper.selectMaxSerialIdWhereTableName(userList.iterator().next().getEnTablename());
        Assert.assertNotNull(id);
    }
}