package com.warrior.luminate.service;

import com.warrior.luminate.entity.XxlJobInfo;
import com.warrior.luminate.vo.BasicResultVO;

/**
 * @author WARRIOR
 * @version 1.0
 */
public interface CronTaskService {
    /**
     * 新增时返回任务Id，修改时无返回
     *
     * @param xxlJobInfo xxlJobInfo
     * @return 新增时返回任务Id，修改时无返回
     */
    BasicResultVO<String> saveCronTask(XxlJobInfo xxlJobInfo);


    /**
     * 删除定时任务
     *
     * @param taskId taskId
     * @return BasicResultVO
     */
    BasicResultVO<?> deleteCronTask(Integer taskId);


    /**
     * 启动定时任务
     *
     * @param taskId taskId
     * @return BasicResultVO
     */
    BasicResultVO<?> startCronTask(Integer taskId);


    /**
     * 暂停定时任务
     *
     * @param taskId taskId
     * @return BasicResultVO
     */
    BasicResultVO<?> stopCronTask(Integer taskId);
}
