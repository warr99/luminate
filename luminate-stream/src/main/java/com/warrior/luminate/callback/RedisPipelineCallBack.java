package com.warrior.luminate.callback;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
public interface RedisPipelineCallBack {

    /**
     * 具体执行逻辑
     *
     * @param redisAsyncCommands 用于执行异步的Redis命令操作
     * @return 异步操作结果
     */
    List<RedisFuture<?>> invoke(RedisAsyncCommands<byte[], byte[]> redisAsyncCommands);
}
