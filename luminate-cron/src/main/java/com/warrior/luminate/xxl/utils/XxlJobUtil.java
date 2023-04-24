package com.warrior.luminate.xxl.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.warrior.luminate.constant.CommonConstant;
import com.warrior.luminate.domain.MessageTemplate;
import com.warrior.luminate.enums.RespStatusEnum;
import com.warrior.luminate.vo.BasicResultVO;
import com.warrior.luminate.xxl.constants.XxlJobConstant;
import com.warrior.luminate.xxl.entity.XxlJobGroup;
import com.warrior.luminate.xxl.entity.XxlJobInfo;
import com.warrior.luminate.xxl.enums.ExecutorRouteStrategyEnum;
import com.warrior.luminate.xxl.enums.MisfireStrategyEnum;
import com.warrior.luminate.xxl.enums.ScheduleTypeEnum;
import com.warrior.luminate.xxl.service.CronTaskService;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class XxlJobUtil {

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.title}")
    private String title;

    private final CronTaskService cronTaskService;

    @Autowired
    public XxlJobUtil(CronTaskService cronTaskService) {
        this.cronTaskService = cronTaskService;
    }

    public XxlJobInfo buildXxlJobInfo(MessageTemplate messageTemplate) {

        String scheduleConf = messageTemplate.getExpectPushTime();
        // 如果没有指定cron表达式，说明立即执行(给到xxl-job延迟20秒的cron表达式)
        if (messageTemplate.getExpectPushTime().equals(String.valueOf(CommonConstant.FALSE))) {
            // 使用SimpleDateFormat格式化成Cron表达式
            scheduleConf = DateUtil.format(DateUtil.offsetSecond(new Date(), XxlJobConstant.DELAY_TIME), CommonConstant.CRON_FORMAT);
        }

        XxlJobInfo xxlJobInfo = XxlJobInfo.builder()
                .jobGroup(queryJobGroupId()).jobDesc(messageTemplate.getName())
                .author(messageTemplate.getCreator())
                .scheduleConf(scheduleConf)
                .scheduleType(ScheduleTypeEnum.CRON.name())
                .misfireStrategy(MisfireStrategyEnum.DO_NOTHING.name())
                .executorRouteStrategy(ExecutorRouteStrategyEnum.CONSISTENT_HASH.name())
                .executorHandler(XxlJobConstant.HANDLER_NAME)
                .executorParam(String.valueOf(messageTemplate.getId()))
                .executorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name())
                .executorTimeout(XxlJobConstant.TIME_OUT)
                .executorFailRetryCount(XxlJobConstant.RETRY_COUNT)
                .glueType(GlueTypeEnum.BEAN.name())
                .triggerStatus(CommonConstant.FALSE)
                .glueRemark(StrUtil.EMPTY)
                .glueSource(StrUtil.EMPTY)
                .alarmEmail(StrUtil.EMPTY)
                .childJobId(StrUtil.EMPTY).build();

        if (Objects.nonNull(messageTemplate.getCronTaskId())) {
            xxlJobInfo.setId(messageTemplate.getCronTaskId());
        }
        return xxlJobInfo;
    }

    private int queryJobGroupId() {
        BasicResultVO<?> basicResultVO = cronTaskService.getGroupId(appName, title);
        //如果获取到的groupId为空,创建group
        if (Objects.isNull(basicResultVO.getData())) {
            XxlJobGroup xxlJobGroup = XxlJobGroup.builder().appname(appName).title(title).addressType(CommonConstant.FALSE).build();
            if (RespStatusEnum.SUCCESS.getCode().equals(cronTaskService.createGroup(xxlJobGroup).getCode())) {
                return (Integer) cronTaskService.getGroupId(appName, title).getData();
            }
        }
        return (Integer) basicResultVO.getData();
    }
}
