package com.kd.xxhyf.notice.core;

import org.springframework.scheduling.annotation.Async;

public interface Notice {
    @Async
    void run(String receiveString);
}
