package com.warrior.luminate.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;

    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Map<String, String> mGet(List<String> keys) {
        HashMap<String, String> result = new HashMap<>(keys.size());
        try {
            List<String> valueList = redisTemplate.opsForValue().multiGet(keys);
            if (valueList != null && CollUtil.isNotEmpty(valueList)) {
                for (int i = 0; i < keys.size(); i++) {
                    String value = valueList.get(i);
                    if (StrUtil.isNotBlank(value)){
                        result.put(keys.get(i), valueList.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void pipelineSetExTime(Map<String, String> keyValueToRedis, Long deduplicationTime) {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (Map.Entry<String, String> entry : keyValueToRedis.entrySet()) {
                connection.setEx(entry.getKey().getBytes(), deduplicationTime, entry.getValue().getBytes());
            }
            return null;
        });
    }
}
