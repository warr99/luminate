package com.warrior.luminate.service;

/**
 * @author WARRIOR
 * @version 1.0
 * 处理定时任务
 */
public interface TaskHandlerService {
    /**
     * 处理具体的逻辑
     *
     * @param messageTemplateId 消息模板id
     */
    void handle(Long messageTemplateId);
}
