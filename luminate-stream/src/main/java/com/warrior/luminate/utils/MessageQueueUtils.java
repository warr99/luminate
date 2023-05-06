package com.warrior.luminate.utils;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

/**
 * @author WARRIOR
 * @version 1.0
 * 消息队列 -> 创建消费者Source
 */
public class MessageQueueUtils {
    /**
     * @param topicName Kafka 主题名称
     * @param groupId   消费者组 ID
     * @param broker    Kafka 代理地址
     * @return KafkaSource 对象
     */
    public static KafkaSource<String> getKafkaConsumer(String topicName, String groupId, String broker) {
        return KafkaSource.<String>builder()
                .setBootstrapServers(broker)
                .setTopics(topicName)
                .setGroupId(groupId)
                //最早的可用偏移量，即从最早的消息开始消费
                .setStartingOffsets(OffsetsInitializer.earliest())
                //消费者的消息反序列化器->字符串反序列化器
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
    }
}
