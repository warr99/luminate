package com.warrior.luminate.service.deduplication.service;

import cn.hutool.core.util.StrUtil;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.service.deduplication.limit.LimitService;
import com.warrior.luminate.enums.DeduplicationTypeEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author WARRIOR
 * @version 1.0
 * 频率去重——一天内一个用户只能收到某个渠道的消息 N 次
 */
@Service
public class FrequencyDeduplicationService extends AbstractDeduplicationService {
    private static final String PREFIX = "FRE";

    @Autowired
    public FrequencyDeduplicationService(@Qualifier("SimpleLimitService") LimitService limitService) {
        this.limitService = limitService;
        this.deduplicationType = DeduplicationTypeEnums.FREQUENCY.getCode();
    }

    /**
     * 业务规则去重 构建key
     * key : receiver + sendChannel
     * 相同的接受者和相同的发送渠道为同一个key
     *
     * @param taskInfo taskInfo
     * @param receiver receiver
     * @return key
     */
    @Override
    public String buildDeduplicationKey(TaskInfo taskInfo, String receiver) {
        return PREFIX + StrUtil.C_UNDERLINE
                + receiver + StrUtil.C_UNDERLINE
                + taskInfo.getSendChannel();
    }


}
