package com.warrior.luminate.handler;

import com.dtp.core.thread.DtpExecutor;
import com.warrior.luminate.config.CronAsyncThreadPoolConfig;
import com.warrior.luminate.service.TaskHandlerService;
import com.warrior.luminate.utils.ThreadPoolUtils;
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
    private final DtpExecutor xxlCronExecutor = CronAsyncThreadPoolConfig.getXxlCronExecutor();
    private final ThreadPoolUtils threadPoolUtils;

    public CronTaskHandler(TaskHandlerService taskHandlerService, ThreadPoolUtils threadPoolUtils) {
        this.taskHandlerService = taskHandlerService;
        this.threadPoolUtils = threadPoolUtils;
    }

    /**
     * 处理定时任务
     */
    @XxlJob("luminateJobHandler")
    public void execute() {
        log.info("CronTaskHandler#execute messageTemplateId:{} cron exec!", XxlJobHelper.getJobParam());
        Long messageTemplateId = Long.valueOf(XxlJobHelper.getJobParam());
        threadPoolUtils.register(xxlCronExecutor);
        xxlCronExecutor.execute(() -> taskHandlerService.handle(messageTemplateId));
    }
}
