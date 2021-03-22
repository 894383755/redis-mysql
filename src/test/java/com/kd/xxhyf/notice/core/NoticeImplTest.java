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

import java.io.*;
import java.lang.reflect.Field;

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
    void run() throws IOException {
        File fileDir = new File("src/test/resources/XMLFile/notice/");
        File[] files = fileDir.listFiles();
        for (File file : files) {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            noticeImpl.run(sb.toString());
        }
    }
}