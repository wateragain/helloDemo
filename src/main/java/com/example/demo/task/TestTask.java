package com.example.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestTask {

    /**
     * 每小时有一次
     */
    @Scheduled(cron = "0 1 * * * ?")
    //fixedRate等参数
    public void statJob() {
        System.out.println("定时任务");
    }
}
