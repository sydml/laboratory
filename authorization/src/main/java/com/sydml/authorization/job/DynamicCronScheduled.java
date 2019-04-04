package com.sydml.authorization.job;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Liuym
 * @date 2019/4/4 0004
 */

@Component
@EnableScheduling
public class DynamicCronScheduled implements SchedulingConfigurer {

    private String cron;

    private Runnable task;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Trigger trigger = triggerContext -> {

            //任务触发，可修改任务的执行周期.
            CronTrigger trigger1 = new CronTrigger(cron);
            Date nextExec = trigger1.nextExecutionTime(triggerContext);
            return nextExec;
        };
        taskRegistrar.addTriggerTask(task, trigger);
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