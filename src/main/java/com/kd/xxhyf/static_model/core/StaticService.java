package com.kd.xxhyf.static_model.core;

import org.springframework.scheduling.annotation.Async;

public interface StaticService {
    @Async
    void run(String receiveString);
}
