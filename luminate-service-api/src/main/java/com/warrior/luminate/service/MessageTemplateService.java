package com.warrior.luminate.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.warrior.luminate.domain.MessageTemplate;
import com.warrior.luminate.vo.BasicResultVO;

/**
 * @author WARRIOR
 * @description 针对表【message_template(消息模板信息)】的数据库操作Service
 * @createDate 2023-04-03 14:14:00
 */
public interface MessageTemplateService extends IService<MessageTemplate> {

    /**
     * 开启某个模板的定时任务
     *
     * @param id 模板id
     * @return BasicResultVO<?> BasicResultVO<?>
     */
    BasicResultVO<?> startCronTask(Long id);


    /**
     * 关闭某个模板的定时任务
     *
     * @param id 模板id
     * @return BasicResultVO<?> BasicResultVO<?>
     */
    BasicResultVO<?> stopCronTask(Long id);

}
