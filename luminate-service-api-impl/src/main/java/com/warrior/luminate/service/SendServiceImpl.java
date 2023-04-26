package com.warrior.luminate.service;


import cn.monitor4all.logRecord.annotation.OperationLog;
import com.warrior.luminate.domain.BatchSendRequest;
import com.warrior.luminate.domain.SendRequest;
import com.warrior.luminate.domain.SendResponse;
import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.pipeline.ProcessContext;
import com.warrior.luminate.pipeline.ProcessController;
import com.warrior.luminate.vo.BasicResultVO;
import org.jetbrains.annotations.NotNull;
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
    @OperationLog(bizType = "SendService#send", bizId = "#sendRequest.messageTemplateId", msg = "#sendRequest")
    public SendResponse send(SendRequest sendRequest) {
        //包装成发送消息任务
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();
        return getSendResponse(sendTaskModel, sendRequest.getCode());
    }

    /**
     * 批量发送消息实现方法
     *
     * @param batchSendRequest 批量发送请求的参数
     * @return SendResponse
     */
    @Override
    @OperationLog(bizType = "SendService#batchSend", bizId = "#batchSendRequest.messageTemplateId", msg = "#batchSendRequest")
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        //包装成发送消息任务
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();

        return getSendResponse(sendTaskModel, batchSendRequest.getCode());
    }

    @NotNull
    private SendResponse getSendResponse(SendTaskModel sendTaskModel, String code) {
        //包装在上下文中
        ProcessContext<SendTaskModel> processContext = new ProcessContext<>();
        processContext
                .setCode(code)
                .setNeedBreak(false)
                .setProcessModel(sendTaskModel)
                .setResponse(BasicResultVO.success());
        //执行责任链中的active
        ProcessContext<SendTaskModel> process = processController.process(processContext);
        //返回执行结果
        return new SendResponse(process.getResponse().getCode(), process.getResponse().getMsg());
    }
}
