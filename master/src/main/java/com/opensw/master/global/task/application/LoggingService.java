package com.opensw.master.global.task.application;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    @Async("executor")
    public void executeThreads() {
        System.out.println("executing threads");

        System.out.println("[TestService2]" + Thread.currentThread().getName());
    }
}
