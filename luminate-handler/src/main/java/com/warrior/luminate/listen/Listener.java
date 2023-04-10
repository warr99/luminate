package com.warrior.luminate.listen;

import com.alibaba.fastjson.JSON;
import com.warrior.luminate.domain.TaskInfo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import com.warrior.luminate.utils.GroupIdUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Component
public class Listener {
    @KafkaListener(topics = "#{'${luminate.topic.name}'}")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            List<TaskInfo> taskInfoList = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdUtils.getGroupIdByTaskInfo(taskInfoList.get(0));
            //每个消费者组只消费自己的消息
            if (topicGroupId.equals(messageGroupId)) {
                //TODO 消费者组处理消息
                System.out.println("topicGroupId: " + topicGroupId + "; handle message: " + taskInfoList.toString());
            }
        }
    }
}
