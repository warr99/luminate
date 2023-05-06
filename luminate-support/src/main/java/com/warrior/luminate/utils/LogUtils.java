package com.warrior.luminate.utils;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.CustomLogListener;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.warrior.luminate.domain.AnchorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 * 日志工具类
 */
@Slf4j
@Component
public class LogUtils extends CustomLogListener {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${luminate.log.topic.name}")
    private String topicName;

    public LogUtils(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 方法切面的日志 @OperationLog 所产生
     */
    @Override
    public void createLog(LogDTO logDTO) throws Exception {
        log.info(JSON.toJSONString(logDTO));
    }

    public void recordAnchorLog(AnchorInfo anchorInfo) {
        anchorInfo.setLogTimestamp(System.currentTimeMillis());
        String message = JSON.toJSONString(anchorInfo);
        log.info(message);
        try {
            kafkaTemplate.send(topicName, message);
        } catch (Exception e) {
            log.error("LogUtils#print send mq fail! e:{},params:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(anchorInfo));
        }
    }

}
