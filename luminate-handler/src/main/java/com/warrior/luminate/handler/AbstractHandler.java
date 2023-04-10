package com.warrior.luminate.handler;

import com.warrior.luminate.domain.TaskInfo;

/**
 * @author WARRIOR
 * @version 1.0
 */
public abstract class AbstractHandler {
    /**
     * 统一处理的handler接口
     *
     * @param taskInfo 消息任务信息
     */
    public abstract void handler(TaskInfo taskInfo);
}
