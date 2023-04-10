package com.warrior.luminate.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.enums.RespStatusEnum;
import com.warrior.luminate.pipeline.Handler;
import com.warrior.luminate.pipeline.ProcessContext;
import com.warrior.luminate.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Component
public class SendMqHandler implements Handler<SendTaskModel> {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${luminate.topic.name}")
    private String topicName;

    public SendMqHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        try {
            kafkaTemplate.send(topicName, JSON.toJSONString(sendTaskModel.getTaskInfo(),
                    SerializerFeature.WriteClassName));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
        }
    }
}
