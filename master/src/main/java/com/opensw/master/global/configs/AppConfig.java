package com.opensw.master.global.configs;

import com.opensw.master.global.task.application.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Random;

@Configuration
@Profile("master")
public class AppConfig {

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            LoggingService loggingService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
//                boolean stop = false;
//                while (!stop) { // true를 의미
//                    try {
//                        Thread.sleep(100);  // 0.1초동안 일시정지 됨.
//                        loggingService.innerMethodCall(new Random().ints(1000, 9999).findAny().getAsInt());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        stop = true;
//                    }
//                }
            }
        };
    }
}
