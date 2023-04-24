package com.warrior.luminate.config;

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class ThreadPoolConfig {
    /**
     * setWorkQueue(new LinkedBlockingQueue<>(queueSize)):
     * 这个方法设置任务队列。当线程池中的所有线程都处于忙碌状态时，新的任务将被放入任务队列中等待执行。
     * 这里使用了一个无界的阻塞队列LinkedBlockingQueue，它可以无限制地存放任务。
     * <p>
     * setHandler(new ThreadPoolExecutor.CallerRunsPolicy()):
     * 这个方法设置线程池的饱和策略。当线程池中的线程数已经达到最大线程数，并且任务队列已经满了，
     * 新提交的任务将根据饱和策略进行处理。这里使用的是CallerRunsPolicy，表示当线程池已满时，任务将在提交任务的线程中运行。
     *
     * @param coreSize  核心线程数
     * @param maxSize   最大线程数
     * @param queueSize 任务队列大小
     * @return ExecutorService
     */
    public static ExecutorService getThreadPool(Integer coreSize, Integer maxSize, Integer queueSize) {
        return ExecutorBuilder.create()
                .setCorePoolSize(coreSize)
                .setMaxPoolSize(maxSize)
                .setKeepAliveTime(60, TimeUnit.SECONDS)
                .setWorkQueue(new LinkedBlockingQueue<>(queueSize))
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
    }
}

