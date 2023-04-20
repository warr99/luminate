package com.warrior.luminate.service.deduplication.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.enums.DeduplicationTypeEnums;
import com.warrior.luminate.service.deduplication.limit.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author WARRIOR
 * @version 1.0
 * 内容去重——过滤5分钟内发给统一用户相同内容的信息
 */

@Service
public class ContentDeduplicationService extends AbstractDeduplicationService {

    @Autowired
    public ContentDeduplicationService(@Qualifier("SlideWindowLimitService") LimitService limitService) {
        this.limitService = limitService;
        this.deduplicationType = DeduplicationTypeEnums.CONTENT.getCode();
    }

    /**
     * 构建内容去重的 key
     * key: md5(templateId + receiver + content)
     * 将相同的模板id、接收者和消息内容取MD5值作为一个key
     *
     * @param taskInfo taskInfo
     * @return key
     */
    @Override
    public String buildDeduplicationKey(TaskInfo taskInfo, String receiver) {
        return DigestUtil.md5Hex(taskInfo.getMessageTemplateId() + receiver + JSON.toJSONString(taskInfo.getContentModel()));
    }
}
