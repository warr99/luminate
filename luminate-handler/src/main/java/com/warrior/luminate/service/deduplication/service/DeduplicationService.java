package com.warrior.luminate.service.deduplication.service;

import com.warrior.luminate.domian.DeduplicationParam;

/**
 * @author WARRIOR
 * @version 1.0
 */
public interface DeduplicationService {
    /**
     * 去重操作
     *
     * @param param 去重需要的参数
     */
    void deduplication(DeduplicationParam param);
}
