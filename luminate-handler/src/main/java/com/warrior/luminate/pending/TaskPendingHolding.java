package com.warrior.luminate.pending;

import com.dtp.core.thread.DtpExecutor;
import com.warrior.luminate.config.HandlerThreadPoolConfig;
import com.warrior.luminate.utils.GroupIdUtils;
import com.warrior.luminate.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class TaskPendingHolding {
    private final Map<String, ExecutorService> taskPendingHolder = new HashMap<>(32);
    private final ThreadPoolUtils threadPoolUtils;
    private static final List<String> GROUP_IDS = GroupIdUtils.getAllGroupId();

    public TaskPendingHolding(ThreadPoolUtils threadPoolUtils) {
        this.threadPoolUtils = threadPoolUtils;
    }


    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     */
    @PostConstruct
    public void init() {
        for (String groupId : GROUP_IDS) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            taskPendingHolder.put(groupId, executor);
            threadPoolUtils.register(executor);
        }
    }

    /**
     * 得到对应的线程池
     *
     * @param groupId groupId
     * @return 对应的线程池
     */
    public ExecutorService getExecutorThreadPool(String groupId) {
        return taskPendingHolder.get(groupId);
    }
}
