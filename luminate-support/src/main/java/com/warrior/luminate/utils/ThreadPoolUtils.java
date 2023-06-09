package com.warrior.luminate.utils;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import com.warrior.luminate.shutdown.ThreadPoolExecutorShutdownDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 * 线程池工具类
 */
@Component
public class ThreadPoolUtils {
    private static final String SOURCE_NAME = "luminate";
    private final ThreadPoolExecutorShutdownDefinition shutdownDefinition;

    @Autowired
    public ThreadPoolUtils(ThreadPoolExecutorShutdownDefinition threadPoolExecutorShutdownDefinition) {
        this.shutdownDefinition = threadPoolExecutorShutdownDefinition;
    }

    /**
     * 1. 将当前线程池 加入到 动态线程池内
     * 2. 注册 线程池 被Spring管理，优雅关闭
     */
    public void register(DtpExecutor dtpExecutor) {
        DtpRegistry.register(dtpExecutor, SOURCE_NAME);
        shutdownDefinition.registryExecutor(dtpExecutor);
    }
}
