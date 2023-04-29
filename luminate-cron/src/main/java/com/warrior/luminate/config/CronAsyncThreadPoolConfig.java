package com.warrior.luminate.config;

import cn.hutool.core.thread.ExecutorBuilder;
import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.thread.DtpExecutor;
import com.dtp.core.thread.ThreadPoolBuilder;
import com.warrior.luminate.constant.ThreadPoolConstant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author WARRIOR
 * @version 1.0
 * 异步处理定时任务的线程池配置管理
 */

public class CronAsyncThreadPoolConfig {
    public static final String EXECUTE_XXL_THREAD_POOL_NAME = "execute-xxl-thread-pool";

    /**
     * 业务：消费pending队列实际的线程池
     * 配置：核心线程可以被回收，当线程池无被引用且无核心线程数，应当被回收
     * 动态线程池且被Spring管理：false
     *
     * @return 消费pending队列实际的线程池
     */
    public static ExecutorService getConsumePendingThreadPool() {
        return ExecutorBuilder.create()
                .setCorePoolSize(ThreadPoolConstant.COMMON_CORE_POOL_SIZE)
                .setMaxPoolSize(ThreadPoolConstant.COMMON_MAX_POOL_SIZE)
                .setWorkQueue(ThreadPoolConstant.BIG_BLOCKING_QUEUE)
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .setAllowCoreThreadTimeOut(true)
                .setKeepAliveTime(ThreadPoolConstant.SMALL_KEEP_LIVE_TIME, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 业务：接收到xxl-job请求的线程池
     * 配置：不丢弃消息，核心线程数不会随着keepAliveTime而减少(不会被回收)
     * 动态线程池且被Spring管理：true
     *
     * @return 接收到xxl-job请求的线程池
     */
    public static DtpExecutor getXxlCronExecutor() {
        return ThreadPoolBuilder.newBuilder()
                .threadPoolName(EXECUTE_XXL_THREAD_POOL_NAME)
                .corePoolSize(ThreadPoolConstant.COMMON_CORE_POOL_SIZE)
                .maximumPoolSize(ThreadPoolConstant.COMMON_MAX_POOL_SIZE)
                .keepAliveTime(ThreadPoolConstant.COMMON_KEEP_LIVE_TIME)
                .timeUnit(TimeUnit.SECONDS)
                .rejectedExecutionHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName())
                .allowCoreThreadTimeOut(false)
                .workQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(), ThreadPoolConstant.COMMON_QUEUE_SIZE, false)
                .buildDynamic();
    }

    /**
     * 业务：实现pending队列的单线程池
     * 配置：核心线程可以被回收，当线程池无被引用且无核心线程数，应当被回收
     * 动态线程池且被Spring管理：false
     * @return 实现pending队列的单线程池
     */
    public static ExecutorService getPendingSingleThreadPool() {
        return ExecutorBuilder.create()
                .setCorePoolSize(ThreadPoolConstant.SINGLE_CORE_POOL_SIZE)
                .setMaxPoolSize(ThreadPoolConstant.SINGLE_MAX_POOL_SIZE)
                .setWorkQueue(ThreadPoolConstant.BIG_BLOCKING_QUEUE)
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .setAllowCoreThreadTimeOut(true)
                .setKeepAliveTime(ThreadPoolConstant.SMALL_KEEP_LIVE_TIME, TimeUnit.SECONDS)
                .build();
    }
}
