package com.warrior.luminate.pending;

import com.warrior.luminate.config.ThreadPoolConfig;
import com.warrior.luminate.utils.GroupIdUtils;
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
    private final static Integer CORE_SIZE = 3;
    private final static Integer MAX_SIZE = 3;
    private final static Integer QUEUE_SIZE = 100;
    private final Map<String, ExecutorService> taskPendingHolder = new HashMap<>(32);
    private static final List<String> GROUP_IDS = GroupIdUtils.getAllGroupId();


    /**
     * 给每个渠道，每种消息类型初始化一个线程池

     */
    @PostConstruct
    public void init() {
        for (String groupId : GROUP_IDS) {
            taskPendingHolder.put(groupId, ThreadPoolConfig.getThreadPool(CORE_SIZE, MAX_SIZE, QUEUE_SIZE));
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
