package com.warrior.luminate.service.deduplication.limit;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.service.deduplication.service.AbstractDeduplicationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author WARRIOR
 * @version 1.0
 */
public abstract class AbstractLimitService implements LimitService {
    /**
     * 构建去重的Key
     *
     * @param taskInfo taskInfo
     * @param receiver receiver
     * @return key
     */
    protected String buildDeduplicationKey(AbstractDeduplicationService service, TaskInfo taskInfo, String receiver) {
        return service.buildDeduplicationKey(taskInfo, receiver);
    }

    protected List<String> getAllKey(AbstractDeduplicationService service, TaskInfo taskInfo) {
        Set<String> receiverSet = taskInfo.getReceiver();
        ArrayList<String> keyList = new ArrayList<>(receiverSet.size());
        for (String receiver : receiverSet) {
            String key = buildDeduplicationKey(service, taskInfo, receiver);
            keyList.add(key);
        }
        return keyList;
    }
}
