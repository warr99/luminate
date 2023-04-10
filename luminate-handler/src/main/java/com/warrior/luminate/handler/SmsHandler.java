package com.warrior.luminate.handler;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.enums.ChannelTypeEnums;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class SmsHandler extends AbstractHandler {

    public SmsHandler() {
        channelCode = ChannelTypeEnums.SMS.getCode();
    }


    @Override
    public void handler(TaskInfo taskInfo) {
        //TODO 处理 SMS 短信任务
        System.out.println("com.warrior.luminate.handler() handle SMS");
    }
}
