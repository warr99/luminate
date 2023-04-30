package com.warrior.luminate.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author WARRIOR
 * @version 1.0
 * 优雅关闭线程池
 */

@Slf4j
@Component
public class ThreadPoolExecutorShutdownDefinition implements ApplicationListener<ContextClosedEvent> {


    private final List<ExecutorService> POOLS = Collections.synchronizedList(new ArrayList<>(12));


    /**
     * 用于将线程池注册到 POOLS
     *
     * @param executor 线程池
     */
    public void registryExecutor(ExecutorService executor) {
        POOLS.add(executor);
    }

    @Override
    public void onApplicationEvent(@NotNull ContextClosedEvent event) {
        log.info("容器关闭前处理线程池优雅关闭开始, 当前要处理的线程池数量为: {} >>>>>>>>>>>>>>>>", POOLS.size());
        if (CollectionUtils.isEmpty(POOLS)) {
            return;
        }
        for (ExecutorService pool : POOLS) {
            pool.shutdown();
            try {
                //阻塞方法，等待当前线程池中的任务执行完毕或者超时，最长等待时间由 awaitTermination 和 TIME_UNIT 指定
                long awaitTermination = 20;
                if (!pool.awaitTermination(awaitTermination, TimeUnit.SECONDS)) {
                    log.warn("Timed out while waiting for executor [{}] to terminate", pool);
                }
            } catch (InterruptedException ex) {
                log.warn("Timed out while waiting for executor [{}] to terminate", pool);
                Thread.currentThread().interrupt();
            }
        }
    }
}
