package com.sydml.authorization.job;

import com.sydml.authorization.job.domain.DynamicJob;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

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

    private volatile ScheduledTaskRegistrar registrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        this.registrar = taskRegistrar;
    }

    public void addTask(DynamicJob task, String cron) {
        Trigger trigger = triggerContext -> {
            //任务触发，可修改任务的执行周期.
            CronTrigger CronTrigger = new CronTrigger(cron);
            Date nextExec = CronTrigger.nextExecutionTime(triggerContext);
            return nextExec;
        };
        if (task != null) {
            registrar.addTriggerTask(task, trigger);
            registrar.getScheduler().schedule(task, trigger);
        }
    }

    /**
     * 停止指定task,这里停止的意义不大，实际业务中如果停止定时任务，要做回滚操作，不如执行完毕，错误的数据进行修复
     * @param taskName
     */
    public void stopTask(String taskName) {
        Set<ScheduledTask> scheduledTasks = registrar.getScheduledTasks();
        for (ScheduledTask scheduledTask : scheduledTasks) {
            Task task = scheduledTask.getTask();
            DynamicJob job = (DynamicJob) task.getRunnable();
            if (job.getName().equals(taskName)) {
                scheduledTask.cancel();
            }
        }
    }

}