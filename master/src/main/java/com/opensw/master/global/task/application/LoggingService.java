package com.opensw.master.global.task.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Profile("master")
public class LoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

    @PostConstruct
    public void init() {
        try {
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                LOGGER.info("async ransom int = " + new Random().ints(1000, 9999).findAny().getAsInt());
            }, 100, 100, TimeUnit.MILLISECONDS);    // 0.1초 뒤 바로 실행
        } catch (Exception e) {
            LOGGER.error("Exception while accessing logging", e);
        }
    }
}
