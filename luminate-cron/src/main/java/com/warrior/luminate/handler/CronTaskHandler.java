package com.warrior.luminate.handler;

import com.warrior.luminate.service.TaskHandlerService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
@Service
public class CronTaskHandler {
    private final TaskHandlerService taskHandlerService;

    public CronTaskHandler(TaskHandlerService taskHandlerService) {
        this.taskHandlerService = taskHandlerService;
    }

    /**
     * 处理定时任务
     */
    @XxlJob("luminateJobHandler")
    public void execute() {
        Long messageTemplateId = Long.valueOf(XxlJobHelper.getJobParam());
        log.info("CronTaskHandler#execute messageTemplateId:{} cron exec!", XxlJobHelper.getJobParam());
        taskHandlerService.handle(messageTemplateId);
    }
}
