package com.warrior.luminate.handler;

import com.warrior.luminate.domain.TaskInfo;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class SmsHandler extends AbstractHandler {


    @Override
    public void handler(TaskInfo taskInfo) {
        //TODO 处理 SMS 短信任务
        System.out.println("com.warrior.luminate.handler sms");
    }
}
