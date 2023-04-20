package com.warrior.luminate.service.deduplication.limit;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.service.deduplication.service.AbstractDeduplicationService;
import com.warrior.luminate.utils.RedisUtil;
import com.warrior.luminate.utils.SnowFlakeIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Service(value = "SlideWindowLimitService")
public class SlideWindowLimitService extends AbstractLimitService {

    private final RedisUtil redisUtil;

    private final SnowFlakeIdUtil snowFlakeIdUtils = new SnowFlakeIdUtil(1, 1);

    private DefaultRedisScript<Long> redisScript;

    @Autowired

    public SlideWindowLimitService(RedisUtil redisUtils) {
        this.redisUtil = redisUtils;
    }


    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
    }

    @Override
    public Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param) {
        Set<String> filterReceiver = new HashSet<>(taskInfo.getReceiver().size());
        long nowTime = System.currentTimeMillis();
        for (String receiver : taskInfo.getReceiver()) {
            //zset的key
            String key = buildDeduplicationKey(service, taskInfo, receiver);
            //使用雪花算法生成唯一的value
            String scoreValue = String.valueOf(snowFlakeIdUtils.nextId());
            //score为当前时间戳
            String score = String.valueOf(nowTime);
            if (redisUtil.executeLua(redisScript, Collections.singletonList(key), String.valueOf(param.getDeduplicationTime() * 1000), score, String.valueOf(param.getCountNum()), scoreValue)) {
                filterReceiver.add(receiver);
            }

        }
        return filterReceiver;
    }
}
