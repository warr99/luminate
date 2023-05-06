package com.warrior.luminate.constants;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class LuminateFlinkConstant {
    /**
     * Kafka 相关
     */
    public static final String TOPIC_NAME = "luminateLog";
    public static final String GROUP_ID = "luminateLogGroup";
    public static final String BROKER = "192.168.20.160:9092";

    /**
     * flink 相关
     */
    public static final String SOURCE_NAME = "luminate_kafka_source";
    public static final String FUNCTION_NAME = "luminate_transfer";
    public static final String SINK_NAME = "luminate_sink";
    public static final String JOB_NAME = "luminateBootstrap";


    /**
     * Redis 相关
     */
    public static final String REDIS_IP = "192.168.20.160";
    public static final int REDIS_PORT = 6379;
    public static final String REDIS_PASSWORD = "CXr123456_redis";
}
