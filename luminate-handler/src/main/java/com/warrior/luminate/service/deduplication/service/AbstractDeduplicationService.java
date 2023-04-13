package com.warrior.luminate.service.deduplication.service;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.warrior.luminate.constant.LuminateConstant;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Slf4j
public abstract class AbstractDeduplicationService implements DeduplicationService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 构建去重的Key
     *
     * @param taskInfo taskInfo
     * @param receiver receiver
     * @return key
     */
    protected abstract String buildDeduplicationKey(TaskInfo taskInfo, String receiver);

    private List<String> getAllKey(TaskInfo taskInfo) {
        Set<String> receiverSet = taskInfo.getReceiver();
        ArrayList<String> keyList = new ArrayList<>(receiverSet.size());
        for (String receiver : receiverSet) {
            String key = buildDeduplicationKey(taskInfo, receiver);
            keyList.add(key);
        }
        return keyList;
    }

    /**
     * 去重实现
     *
     * @param param 去重需要的参数
     */
    @Override
    public void deduplication(DeduplicationParam param) {

        TaskInfo taskInfo = param.getTaskInfo();

        Set<String> receiverSet = taskInfo.getReceiver();
        Set<String> filterReceiver = new HashSet<>(receiverSet.size());
        Set<String> readyPutRedisReceiver = new HashSet<>(receiverSet.size());

        List<String> keyList = getAllKey(taskInfo);
        Map<String, String> keyValueMap = redisUtil.mGet(keyList);
        for (String receiver : receiverSet) {
            String key = buildDeduplicationKey(taskInfo, receiver);
            String value = keyValueMap.get(key);
            //在redis中存在且已经大于等于countNum->加入去重列表
            if (value != null && Integer.parseInt(value) >= param.getCountNum()) {
                log.info("filter receiver: {}, deduplication: {}, content: {}",
                        receiver,
                        this.getClass().toString(),
                        JSON.toJSONString(taskInfo));
                filterReceiver.add(receiver);
            } else {
                //在redis中不存在或者小于countNum
                readyPutRedisReceiver.add(receiver);
            }
        }
        putInRedis(readyPutRedisReceiver, keyValueMap, param);
        receiverSet.removeAll(filterReceiver);

    }

    private void putInRedis(Set<String> readyPutRedisReceiver, Map<String, String> keyValueMap, DeduplicationParam param) {
        Map<String, String> keyValueToRedis = new HashMap<>(readyPutRedisReceiver.size());
        for (String receiver : readyPutRedisReceiver) {
            String key = buildDeduplicationKey(param.getTaskInfo(), receiver);
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
