package com.warrior.luminate.pending;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AbstractDelayedProcess<T> {

    /**
     * 批量装载任务
     */
    private List<T> taskList = new ArrayList<>();

    /**
     * 批量处理消费者的相关参数
     */
    protected PendingParam<T> pendingParam;

    /**
     * 上次执行的时间
     */
    private final Long lastHandleTime = System.currentTimeMillis();

    /**
     * 将元素放入阻塞队列中
     *
     * @param t t
     */
    public void putIntoQueue(T t) {
        try {
            pendingParam.getBlockingDeque().put(t);
        } catch (InterruptedException e) {
            log.error("Pending#pending error:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 从阻塞队列中消费待处理任务
     */
    @PostConstruct
    public void consumeProcess() {
//        while (true) {
//            try {
//                BlockingDeque<T> blockingDeque = pendingParam.getBlockingDeque();
//                //从阻塞队列中获取一个待处理的任务(若队列为空,指定等待时间为`timeThreshold`(毫秒))
//                T task = blockingDeque.poll(pendingParam.getTimeThreshold(), TimeUnit.MILLISECONDS);
//                if (task != null) {
//                    //将待处理任务添加到待处理任务列表
//                    taskList.add(task);
//                }
//                //判断是否满足批处理的条件
//                if (CollUtil.isNotEmpty(taskList) && isBatchTaskReady()) {
//                    List<T> pendingList = taskList;
//                    //清空待处理任务队列
//                    taskList = Lists.newArrayList();
//                    //执行待处理任务
//                    pendingParam.executorService.execute(() -> process(pendingList));
//                }
//            } catch (Exception e) {
//                log.error("Pending#initConsumePending failed:{}", Throwables.getStackTraceAsString(e));
//            }
//
//        }
    }

    /**
     * 判断是否进行批处理 -> 当待处理队列中任务的数量 > numThreshold || 距离上次执行时间 > timeThreshold
     *
     * @return 是否进行批处理
     */
    private boolean isBatchTaskReady() {
        return
                taskList.size() > pendingParam.getNumThreshold() ||
                        (System.currentTimeMillis() - lastHandleTime) > pendingParam.getTimeThreshold();
    }

    public void process(List<T> pendingList) {
        if (ArrayUtil.isEmpty(pendingList)) {
            return;
        }
        doHandle(pendingList);
    }

    /**
     * 处理阻塞队列的元素 真正方法
     *
     * @param pendingList 待处理队列
     */
    public abstract void doHandle(List<T> pendingList);

}
