package com.warrior.luminate.constants;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class PendingConstant {
    /**
     * 阻塞队列大小
     */
    public static final Integer QUEUE_SIZE = 100;

    /**
     * 触发执行的数量阈值
     */
    public static final Integer NUM_THRESHOLD = 100;

    /**
     * 触发执行的时间阈值(毫秒)
     */
    public static final Long TIME_THRESHOLD = 5000L;
    /**
     * 真正消费线程池配置的信息
     */
    public static final Integer CORE_POOL_SIZE = 2;
    public static final Integer MAX_POOL_SIZE = 2;
    public static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingQueue<>(5);
}
