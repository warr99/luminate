package com.warrior.luminate.pending;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;


/**
 * @author WARRIOR
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PendingParam<T> {
    /**
     * 阻塞队列——用于实现“生产者-消费者”
     */
    private BlockingDeque<T> blockingDeque;

    /**
     * 触发批量执行的数量阈值
     */
    private Integer numThreshold;

    /**
     * 触发执行的时间阈值(毫秒)
     */
    private Long timeThreshold;

    /**
     * 消费线程池实例(用于实际处理消费者)
     */
    protected ExecutorService executorService;
}
