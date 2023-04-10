package com.warrior.luminate.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.warrior.luminate.domain.MessageTemplate;
import com.warrior.luminate.service.MessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WARRIOR
 * @version 1.0
 */
@RestController
public class MessageTemplateController {
    private final MessageTemplateService messageTemplateService;

    @Autowired
    public MessageTemplateController(MessageTemplateService messageTemplateService ) {
        this.messageTemplateService = messageTemplateService;
    }

    /**
     * test insert
     */
    @GetMapping("/insert")
    public String insert() {

        MessageTemplate messageTemplate = MessageTemplate.builder()
                .name("test message")
                .auditStatus(10)
                .flowId("flow")
                .msgStatus(30)
                .idType(30)
                .sendChannel(30)
                .templateType(10)
                .msgType(10)
                .expectPushTime("0")
                .msgContent("{\"content\":\"{$content}\",\"url\":\"www.baidu.com\",\"title\":\"cxr\"}")
                .sendAccount(66)
                .creator("warrior")
                .updator("warrior")
                .team("warrior team")
                .proposer("warrior")
                .auditor("warrior")
                .isDeleted(0)
                .created(Math.toIntExact(DateUtil.currentSeconds()))
                .updated(Math.toIntExact(DateUtil.currentSeconds()))
                .deduplicationTime(1)
                .isNightShield(0)
                .build();

        boolean save = messageTemplateService.save(messageTemplate);

        return JSON.toJSONString(save);

    }

    /**
     * test query
     */
    @GetMapping("/query")
    public String query() {
        Iterable<MessageTemplate> all = messageTemplateService.list();
        for (MessageTemplate messageTemplate : all) {
            return JSON.toJSONString(messageTemplate);
        }
        return null;
    }
}
