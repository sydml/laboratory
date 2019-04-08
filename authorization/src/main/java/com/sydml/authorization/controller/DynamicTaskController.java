package com.sydml.authorization.controller;

import com.sydml.authorization.job.DynamicCronScheduled;
import com.sydml.authorization.job.domain.DynamicJob;
import com.sydml.authorization.platform.job.DynamicTask;
import com.sydml.authorization.job.domain.DynamicJob1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liuym
 * @date 2019/4/4 0004
 */
@RestController
@RequestMapping("dynamic-scheduled")
public class DynamicTaskController {
    @Autowired
    private DynamicCronScheduled dynamicCronScheduled;

    @Autowired
    private DynamicTask dynamicTask;
    @GetMapping("set-cron")
    public void setCron(String cron) {
        DynamicJob job = new DynamicJob();
        dynamicCronScheduled.setTask(job);
        dynamicCronScheduled.setCron(cron);
    }

    @RequestMapping("start")
    public void startDynamicJob(String cron) {
        DynamicJob job = new DynamicJob();
        dynamicTask.startCron(job, cron);
    }
    @GetMapping("start-another-job")
    public void startDynamicJob1(String cron) {
        DynamicJob1 job = new DynamicJob1();
        dynamicTask.startCron(job, cron);
    }

}
