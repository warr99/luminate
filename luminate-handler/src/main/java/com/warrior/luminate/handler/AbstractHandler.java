package com.warrior.luminate.handler;

import com.warrior.luminate.domain.AnchorInfo;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.enums.AnchorStateEnums;
import com.warrior.luminate.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author WARRIOR
 * @version 1.0
 */
public abstract class AbstractHandler {

    @Autowired
    private HandlerHolder handlerHolder;
    @Autowired
    private LogUtils logUtils;


    protected Integer channelCode;

    public void doHandler(TaskInfo taskInfo) {
        boolean isSuccess = handler(taskInfo);
        if (isSuccess) {
            logUtils.recordAnchorLog(AnchorInfo.builder()
                    .state(AnchorStateEnums.SEND_SUCCESS.getCode())
                    .businessId(taskInfo.getBusinessId())
                    .ids(taskInfo.getReceiver())
                    .build());
            return;
        }
        logUtils.recordAnchorLog(AnchorInfo.builder()
                .state(AnchorStateEnums.SEND_FAIL.getCode())
                .businessId(taskInfo.getBusinessId())
                .ids(taskInfo.getReceiver())
                .build());
    }

    /**
     * 统一处理的handler接口
     *
     * @param taskInfo 消息任务信息
     * @return boolean
     */
    public abstract boolean handler(TaskInfo taskInfo);

    @PostConstruct
    private void init() {
        handlerHolder.putHandler(channelCode, this);
    }
}
