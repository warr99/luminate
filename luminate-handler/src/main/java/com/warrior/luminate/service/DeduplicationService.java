package com.warrior.luminate.service;

import cn.hutool.core.date.DateUtil;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author WARRIOR
 * @version 1.0
 * 去重
 */
@Service
public class DeduplicationService {
    private final FrequencyDeduplicationRule frequencyDeduplicationRule;

    private final ContentDeduplicationRule contentDeduplicationRule;

    @Autowired
    public DeduplicationService(FrequencyDeduplicationRule frequencyDeduplicationRule, ContentDeduplicationRule contentDeduplicationRule) {
        this.frequencyDeduplicationRule = frequencyDeduplicationRule;
        this.contentDeduplicationRule = contentDeduplicationRule;
    }

    public void duplication(TaskInfo taskInfo) {
        //TODO bug:当一天的最后5分钟发送了消息给用户通过了内容去重但没通过频率去重，
        // 等到第二天的前五分钟再次发送相同的消息时，可能无法通过内容去重(key在redis中已存在,且value为1)
        //内容去重 -> 过滤5分钟内发给统一用户相同内容的信息
        DeduplicationParam contentParams = DeduplicationParam.builder()
                .deduplicationTime(300L).countNum(1).taskInfo(taskInfo)
                .build();
        contentDeduplicationRule.deduplication(contentParams);
        //频率去重 -> 一天内一个用户只能收到某个渠道的消息 5 次
        //过期时间 —> 从当前时间到当天结束时间的剩余秒数
        Long seconds = (DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000;
        DeduplicationParam businessParams = DeduplicationParam.builder()
                .deduplicationTime(seconds).countNum(5).taskInfo(taskInfo)
                .build();
        frequencyDeduplicationRule.deduplication(businessParams);
    }
}
