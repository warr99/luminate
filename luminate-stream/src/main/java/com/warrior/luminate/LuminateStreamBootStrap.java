package com.warrior.luminate;

import com.warrior.luminate.domain.AnchorInfo;
import com.warrior.luminate.constants.LuminateFlinkConstant;
import com.warrior.luminate.function.LuminateFlatMapFunction;
import com.warrior.luminate.function.LuminateSinkFunction;
import com.warrior.luminate.utils.MessageQueueUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
public class LuminateStreamBootStrap {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<String> kafkaConsumer = MessageQueueUtils.getKafkaConsumer
                (LuminateFlinkConstant.TOPIC_NAME, LuminateFlinkConstant.GROUP_ID, LuminateFlinkConstant.BROKER);
        //WatermarkStrategy.noWatermarks(): 该策略表示事件流中不会生成任何水印，即系统将不会考虑事件的时间戳信息。
        DataStreamSource<String> dataStreamSourceFromKafka =
                env.fromSource(kafkaConsumer, WatermarkStrategy.noWatermarks(), LuminateFlinkConstant.SOURCE_NAME);

        SingleOutputStreamOperator<AnchorInfo> anchorInfoDataStream = dataStreamSourceFromKafka
                .flatMap(new LuminateFlatMapFunction())
                .name(LuminateFlinkConstant.FUNCTION_NAME);

        anchorInfoDataStream.addSink(new LuminateSinkFunction()).name(LuminateFlinkConstant.SINK_NAME);

        env.execute(LuminateFlinkConstant.JOB_NAME);
    }
}
