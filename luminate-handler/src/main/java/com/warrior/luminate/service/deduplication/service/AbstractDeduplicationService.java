package com.warrior.luminate.service.deduplication.service;

import cn.hutool.core.collection.CollUtil;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.service.deduplication.DeduplicationHolder;
import com.warrior.luminate.service.deduplication.limit.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Slf4j
public abstract class AbstractDeduplicationService implements DeduplicationService {

    protected LimitService limitService;
    protected Integer deduplicationType;
    @Autowired
    private DeduplicationHolder deduplicationHolder;


    @PostConstruct
    private void init() {
        deduplicationHolder.putService(deduplicationType, this);
    }

    /**
     * 构建去重的Key
     *
     * @param taskInfo taskInfo
     * @param receiver receiver
     * @return key
     */
    public abstract String buildDeduplicationKey(TaskInfo taskInfo, String receiver);


    /**
     * 去重实现
     *
     * @param param 去重需要的参数
     */
    @Override
    public void deduplication(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();
        Set<String> receiverSet = taskInfo.getReceiver();
        Set<String> filterReceiver = limitService.limitFilter(this, taskInfo, param);
        if (CollUtil.isNotEmpty(filterReceiver)) {
            log.info("filter receiver {} by {}", filterReceiver.toString(),limitService.toString());
            receiverSet.removeAll(filterReceiver);
        }
    }


}
