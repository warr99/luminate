package com.warrior.luminate.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Service
@Slf4j
public class CronTaskHandler {
    /**
     * 测试任务
     */
    @XxlJob("luminateJobHandler")
    public void execute() {
        log.info("XXL-JOB, Hello World.");
    }
}
