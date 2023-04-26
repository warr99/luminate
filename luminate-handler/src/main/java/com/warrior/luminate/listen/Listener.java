package com.warrior.luminate.listen;

import com.alibaba.fastjson.JSON;
import com.warrior.luminate.domain.AnchorInfo;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.enums.AnchorStateEnums;
import com.warrior.luminate.pending.Task;
import com.warrior.luminate.pending.TaskPendingHolding;
import com.warrior.luminate.utils.LogUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import com.warrior.luminate.utils.GroupIdUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author WARRIOR
 * @version 1.0
 */


public class Listener {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private TaskPendingHolding taskPendingHolding;
    @Autowired
    private LogUtils logUtils;

    @KafkaListener(topics = "#{'${luminate.topic.name}'}")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            List<TaskInfo> taskInfoList = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdUtils.getGroupIdByTaskInfo(taskInfoList.get(0));
            //每个消费者组只消费自己的消息
            if (topicGroupId.equals(messageGroupId)) {
                for (TaskInfo taskInfo : taskInfoList) {
                    //记录锚点信息
                    logUtils.recordAnchorLog(
                            AnchorInfo.builder()
                                    .businessId(taskInfo.getBusinessId())
                                    .ids(taskInfo.getReceiver())
                                    .state(AnchorStateEnums.RECEIVE.getCode())
                                    .build());
                    Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
                    taskPendingHolding.getExecutorThreadPool(topicGroupId).execute(task);
                }
            }
        }
    }
}
