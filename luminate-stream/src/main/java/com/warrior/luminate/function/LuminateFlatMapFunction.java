package com.warrior.luminate.function;

import com.alibaba.fastjson.JSON;
import com.warrior.luminate.domain.AnchorInfo;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class LuminateFlatMapFunction implements FlatMapFunction<String, AnchorInfo> {
    @Override
    public void flatMap(String value, Collector<AnchorInfo> collector) throws Exception {
        AnchorInfo anchorInfo = JSON.parseObject(value, AnchorInfo.class);
        collector.collect(anchorInfo);
    }
}
