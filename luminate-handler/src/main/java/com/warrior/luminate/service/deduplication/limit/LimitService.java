package com.warrior.luminate.service.deduplication.limit;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.service.deduplication.service.AbstractDeduplicationService;

import java.util.Set;

/**
 * @author WARRIOR
 * @version 1.0
 * 具体去重实现的抽象类
 */
public interface LimitService {
    /**
     * 实现去重的方法
     *
     * @param service  去重器对象
     * @param taskInfo taskInfo
     * @param param    去重参数
     * @return 返回不符合条件的手机号码
     */
    Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param);
}
