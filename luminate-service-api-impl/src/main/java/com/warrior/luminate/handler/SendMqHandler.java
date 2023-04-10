package com.warrior.luminate.handler;


import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.pipeline.Handler;
import com.warrior.luminate.pipeline.ProcessContext;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Component
public class SendMqHandler implements Handler<SendTaskModel> {
    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        // TODO 发送数据到消息队列
        System.out.println("发送数据到消息队列");
    }
}
