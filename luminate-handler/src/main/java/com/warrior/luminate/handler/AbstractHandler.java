package com.warrior.luminate.handler;

import com.warrior.luminate.domain.TaskInfo;
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


    protected Integer channelCode;

    /**
     * 统一处理的handler接口
     * @param taskInfo 消息任务信息
     * @return boolean
     */
    public abstract boolean handler(TaskInfo taskInfo);

    @PostConstruct
    private void init() {
        handlerHolder.putHandler(channelCode, this);
    }
}
