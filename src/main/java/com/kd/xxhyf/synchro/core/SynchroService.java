package com.kd.xxhyf.synchro.core;

import org.springframework.scheduling.annotation.Async;

public interface SynchroService {
    @Async
    void run(String receiveString);
}
