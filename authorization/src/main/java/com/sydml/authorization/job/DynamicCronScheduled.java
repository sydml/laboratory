package com.sydml.authorization.job;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 验证部分通过，目前只能动态修改cron,不支持动态启动停止任务
 * 建议使用com.sydml.authorization.platform.job.DynamicTask
 *
 * @author Liuym
 * @date 2019/4/4 0004
 */

@Component
@EnableScheduling
public class DynamicCronScheduled implements SchedulingConfigurer {

    private String cron = "0/30 * * * * *";

    private Runnable task;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        Runnable task = () -> {
            //任务逻辑代码部分.
            System.out.println("TaskCronChange task is running ... "+ LocalDateTime.now());
        };

        Trigger trigger = triggerContext -> {

            //任务触发，可修改任务的执行周期.
            CronTrigger CronTrigger = new CronTrigger(getCron());
            Date nextExec = CronTrigger.nextExecutionTime(triggerContext);
            return nextExec;
        };
        if (getTask() != null) {

            taskRegistrar.addTriggerTask(getTask(), trigger);
        }else{
            taskRegistrar.addTriggerTask(task, trigger);

        }
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }
}