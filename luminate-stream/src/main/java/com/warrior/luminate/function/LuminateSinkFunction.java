package com.warrior.luminate.function;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.warrior.luminate.domain.AnchorInfo;
import com.warrior.luminate.domain.SimpleAnchorInfo;
import io.lettuce.core.RedisFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import com.warrior.luminate.utils.LettuceRedisUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
public class LuminateSinkFunction implements SinkFunction<AnchorInfo> {

    @Override
    public void invoke(AnchorInfo anchorInfo, Context context) throws Exception {
        realTimeData(anchorInfo);
    }

    private void realTimeData(AnchorInfo info) {
        try {
            LettuceRedisUtils.pipeline(redisAsyncCommands -> {
                List<RedisFuture<?>> redisFutures = new ArrayList<>();
                /*
                 * 构建userId维度的链路信息 数据结构list:{key,list}
                 * key:userId,listValue:[{timestamp,state,businessId},{timestamp,state,businessId}]
                 */
                SimpleAnchorInfo simpleAnchorInfo = SimpleAnchorInfo.builder()
                        .businessId(info.getBusinessId())
                        .state(info.getState())
                        .timestamp(info.getLogTimestamp())
                        .build();
                for (String id : info.getIds()) {

                    //将simpleAnchorInfo对象转换为JSON字符串，并将其作为值推送到Redis列表（list）中，使用lpush命令。id.getBytes()是作为列表的键
                    redisFutures.add(redisAsyncCommands.lpush(
                            id.getBytes(),
                            JSON.toJSONString(simpleAnchorInfo).getBytes()));

                    //为键为id的Redis列表设置过期时间。过期时间是当前日期的结束时间与当前时间的差值，单位是秒
                    redisFutures.add(redisAsyncCommands.expire(
                            id.getBytes(),
                            (DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000));
                }
                /*
                 * 构建消息模板维度的链路信息 数据结构hash:{key,hash}
                 * key:businessId,hashValue:{state,stateCount}
                 */
                //使用hincrby命令，将info.getIds().size()（ids列表的长度）增加到Redis哈希（hash）中。哈希的键是info.getBusinessId()，字段是info.getState()
                redisFutures.add(redisAsyncCommands.hincrby(
                        String.valueOf(info.getBusinessId()).getBytes(),
                        String.valueOf(info.getState()).getBytes(), info.getIds().size()));

                //为键为info.getBusinessId()的Redis哈希设置过期时间。过期时间是当前日期加上30天的时间与当前时间的差值，单位是秒
                redisFutures.add(redisAsyncCommands.expire(
                        String.valueOf(info.getBusinessId()).getBytes(),
                        ((DateUtil.offsetDay(new Date(), 30).getTime()) / 1000) - DateUtil.currentSeconds()));

                return redisFutures;
            });
        } catch (Exception e) {
            log.error("LuminateSink#invoke error: {}", Throwables.getStackTraceAsString(e));
        }
    }
}
