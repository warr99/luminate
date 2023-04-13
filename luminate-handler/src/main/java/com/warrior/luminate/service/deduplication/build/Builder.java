package com.warrior.luminate.service.deduplication.build;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;

/**
 * @author WARRIOR
 * @version 1.0
 */
public interface Builder {
    String CONFIG_PRE = "deduplication_";

    /**
     * 根据参数和key构建对应的DeduplicationParam
     *
     * @param configurableDeduplicationParam 可配置参数
     * @param taskInfo                       taskInfo
     * @return DeduplicationParam
     */
    DeduplicationParam build(String configurableDeduplicationParam, TaskInfo taskInfo);
}
