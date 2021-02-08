package com.kd.xxhyf.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class resolveXmlTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    String xml = "";

    @Test
    void alarmXml() {
        Map<String, Object> stringObjectMap = resolveXml.alarmXml(xml);
        Assert.notNull(stringObjectMap,"alarmXml 为空");
        System.out.println(stringObjectMap);
    }

    @Test
    void statusXml() {
    }
}