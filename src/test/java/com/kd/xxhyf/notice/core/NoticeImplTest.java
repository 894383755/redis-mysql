package com.kd.xxhyf.notice.core;

import com.kd.xxhyf.config.MyAsyncConfigurer;
import com.kd.xxhyf.mapper.SysNoticeMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest()
@RunWith(SpringRunner.class)
class NoticeImplTest {
    @Resource
    private NoticeImpl noticeImpl;

    @BeforeEach
    void setUp() {
        System.err.println("开始停止");
        //MyTaskExecutorConfig.shutdown();
    }

    @AfterEach
    void tearDown() {
        while (MyAsyncConfigurer.getActiveCount() != 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void run() {

    }
}