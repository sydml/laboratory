package com.sydml.authorization.platform.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * 这个类已经验证通过
 * 支持启动和停止任务
 * 支持动态增加新任务，动态修改cron
 *
 * @author Liuym
 * @date 2019/4/8 0008
 */
@Component
public class DynamicTask {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public String stopCron() {

        if (future != null) {
            future.cancel(true);
        }
        System.out.println("DynamicTask.stopCron()");
        return "stopCron";
    }

    public String startCron(Runnable task, String cron) {
        future = threadPoolTaskScheduler.schedule(task, new CronTrigger(cron));
        System.out.println("DynamicTask.startCron()");
        return "startCron";
    }

}
