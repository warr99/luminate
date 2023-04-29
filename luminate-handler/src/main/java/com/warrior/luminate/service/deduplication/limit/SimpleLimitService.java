package com.warrior.luminate.service.deduplication.limit;

import cn.hutool.core.collection.CollUtil;
import com.warrior.luminate.constant.LuminateConstant;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.service.deduplication.service.AbstractDeduplicationService;
import com.warrior.luminate.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
@Service(value = "SimpleLimitService")
public class SimpleLimitService extends AbstractLimitService {
    private final RedisUtils redisUtil;

    @Autowired
    public SimpleLimitService(RedisUtils redisUtil) {
        this.redisUtil = redisUtil;
    }


    @Override
    public Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param) {

        Set<String> receiverSet = taskInfo.getReceiver();
        Set<String> filterReceiver = new HashSet<>(receiverSet.size());
        Map<String, String> readyPutRedisReceiver = new HashMap<>(receiverSet.size());

        List<String> keyList = getAllKey(service, taskInfo);
        Map<String, String> keyValueMap = redisUtil.mGet(keyList);
        for (String receiver : receiverSet) {
            String key = buildDeduplicationKey(service, taskInfo, receiver);
            String value = keyValueMap.get(key);
            //在redis中存在且已经大于等于countNum->加入去重列表
            if (value != null && Integer.parseInt(value) >= param.getCountNum()) {
                filterReceiver.add(receiver);
            } else {
                //在redis中不存在或者小于countNum
                readyPutRedisReceiver.put(key, receiver);
            }
        }
        putInRedis(readyPutRedisReceiver, keyValueMap, param);
        return filterReceiver;
    }

    private void putInRedis(Map<String, String> readyPutRedisReceiver,
                            Map<String, String> keyValueMap,
                            DeduplicationParam param) {
        Map<String, String> keyValueToRedis = new HashMap<>(readyPutRedisReceiver.size());
        for (Map.Entry<String, String> entry : readyPutRedisReceiver.entrySet()) {
            String key = entry.getKey();
            if (keyValueMap.containsKey(key)) {
                keyValueToRedis.put(key, String.valueOf(Integer.parseInt(keyValueMap.get(key)) + 1));
            } else {
                keyValueToRedis.put(key, LuminateConstant.ONE);
            }
        }
        if (CollUtil.isNotEmpty(keyValueToRedis)) {
            redisUtil.pipelineSetExTime(keyValueToRedis, param.getDeduplicationTime());
        }
    }
}
