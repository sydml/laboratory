package com.sydml.authorization.controller;

import com.sydml.authorization.job.DynamicCronScheduled;
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
public class DynamicCronScheduledController {
    @Autowired
    private DynamicCronScheduled dynamicCronScheduled;
    @GetMapping("set-cron")
    public void setCron(String cron) {
        dynamicCronScheduled.setCron(cron);
    }

}
