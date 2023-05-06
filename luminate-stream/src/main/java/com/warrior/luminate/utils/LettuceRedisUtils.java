package com.warrior.luminate.utils;

import com.warrior.luminate.callback.RedisPipelineCallBack;
import com.warrior.luminate.constants.LuminateFlinkConstant;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.codec.ByteArrayCodec;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class LettuceRedisUtils {
    /**
     * 初始化 redisClient
     */
    private static final RedisClient REDIS_CLIENT;

    static {
        RedisURI redisUri = RedisURI.Builder.redis(LuminateFlinkConstant.REDIS_IP)
                .withPort(LuminateFlinkConstant.REDIS_PORT)
                .withPassword(LuminateFlinkConstant.REDIS_PASSWORD.toCharArray())
                .build();
        REDIS_CLIENT = RedisClient.create(redisUri);
    }

    public static void pipeline(RedisPipelineCallBack pipelineCallBack) {
        //创建一个与Redis服务器的连接。redisClient是一个RedisClient实例，用于创建连接。ByteArrayCodec是用于序列化和反序列化字节数组的编解码器。
        StatefulRedisConnection<byte[], byte[]> connect = REDIS_CLIENT.connect(new ByteArrayCodec());
        //从连接中获取异步操作的命令对象commands，用于执行Redis命令。
        RedisAsyncCommands<byte[], byte[]> commands = connect.async();
        //通过调用pipelineCallBack的invoke方法，执行具体的Redis命令操作，并返回一个RedisFuture对象列表，表示每个命令的异步结果。
        List<RedisFuture<?>> futures = pipelineCallBack.invoke(commands);
        //将管道中的所有Redis命令发送到服务器
        commands.flushCommands();
        //等待所有的Redis命令异步操作完成。
        LettuceFutures.awaitAll(10, TimeUnit.SECONDS,
                futures.toArray(new RedisFuture[0]));
        //关闭与Redis服务器的连接
        connect.close();
    }
}
