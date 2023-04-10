package com.warrior.luminate.service;


import com.warrior.luminate.domain.SendRequest;
import com.warrior.luminate.domain.SendResponse;
import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.pipeline.ProcessContext;
import com.warrior.luminate.pipeline.ProcessController;
import com.warrior.luminate.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Service
public class SendServiceImpl implements SendService {

    private final ProcessController<SendTaskModel> processController;

    @Autowired
    public SendServiceImpl(ProcessController<SendTaskModel> processController) {
        this.processController = processController;
    }

    /**
     * 发送消息实现方法
     *
     * @param sendRequest 发送请求的参数
     * @return SendResponse
     */
    @Override
    public SendResponse send(SendRequest sendRequest) {
        //包装成发送消息任务
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();
        //包装在上下文中
        ProcessContext<SendTaskModel> processContext = new ProcessContext<>();
        processContext
                .setCode(sendRequest.getCode())
                .setNeedBreak(false)
                .setProcessModel(sendTaskModel)
                .setResponse(BasicResultVO.success());
        //执行责任链中的active
        ProcessContext<SendTaskModel> process = processController.process(processContext);
        //返回执行结果
        return new SendResponse(process.getResponse().getCode(), process.getResponse().getMsg());
    }
}
