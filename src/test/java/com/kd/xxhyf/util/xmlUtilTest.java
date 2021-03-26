package com.kd.xxhyf.util;

import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

class xmlUtilTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    String xml = "";

    @Test
    void alarmXml() {
        Map<String, Object> stringObjectMap = XmlUtil.alarmXml(xml);
        Assert.notNull(stringObjectMap,"alarmXml 为空");
        System.out.println(stringObjectMap);
    }

    @Test
    void statusXml() {
        String str = FileUtil.strChangeFile("src/test/resources/XMLFile/static/input1.xml");
        Map<String, List<Map<String, Object>>> stringListMap = XmlUtil.statusXml(str);
        JSONObject jsonObject = JSONObject.fromObject(stringListMap);
        System.out.println(jsonObject.toString());
    }


}