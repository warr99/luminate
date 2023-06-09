package com.warrior.luminate.pending;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.handler.HandlerHolder;
import com.warrior.luminate.service.deduplication.DeduplicationRuleService;
import com.warrior.luminate.utils.LogUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class Task implements Runnable {
    @Autowired
    private HandlerHolder handlerHolder;
    @Autowired
    private DeduplicationRuleService deduplicationService;


    private TaskInfo taskInfo;

    @Override
    public void run() {
        //消息去重
        deduplicationService.duplication(taskInfo);
        if (taskInfo.getReceiver().size() == 0) {
            return;
        }
        //发送消息
        handlerHolder
                .getHandler(taskInfo.getSendChannel())
                .doHandler(taskInfo);
    }
}
