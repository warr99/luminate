package com.warrior.luminate.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.enums.RespStatusEnum;
import com.warrior.luminate.pipeline.Handler;
import com.warrior.luminate.pipeline.ProcessContext;
import com.warrior.luminate.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Slf4j
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
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, JSON.toJSONString(sendTaskModel.getTaskInfo(),
                    SerializerFeature.WriteClassName));

            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("send kafka success! params:{}", JSON.toJSONString(sendTaskModel.getTaskInfo()));
                }

                @Override
                public void onFailure(@NotNull Throwable ex) {
                    context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
                    log.error("send kafka fail! ,params:{}", JSON.toJSONString(sendTaskModel.getTaskInfo().get(0)));
                }
            });
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send kafka fail! e:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(sendTaskModel.getTaskInfo().get(0)));
        }
    }
}
