package com.opensw.master.global.configs;

import com.opensw.master.global.task.application.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Profile("production")
    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            LoggingService loggingService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                for(int i=0;i<10;i++) {
                    Thread.sleep(100);  // 0.1초동안 일시정지 됨.
                    loggingService.executeThreads();
                }
            }
        };
    }
}
