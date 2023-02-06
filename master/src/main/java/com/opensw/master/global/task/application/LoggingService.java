package com.opensw.master.global.task.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class LoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

    @Autowired
    private Executor executor;

    public void innerMethod(int i) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LOGGER.info("async ransom int = " + i);
    }

    public void innerMethodCall(int i) {
        CompletableFuture.runAsync(()->innerMethod(i), executor);   // Async
    }
}
