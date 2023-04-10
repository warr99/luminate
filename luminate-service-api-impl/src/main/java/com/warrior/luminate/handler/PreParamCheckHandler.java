package com.warrior.luminate.handler;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.warrior.luminate.constant.LuminateConstant;
import com.warrior.luminate.domain.MessageParam;
import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.domain.enums.RespStatusEnum;
import com.warrior.luminate.pipeline.Handler;
import com.warrior.luminate.pipeline.ProcessContext;
import com.warrior.luminate.vo.BasicResultVO;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author WARRIOR
 * @version 1.0
 * 前置参数检查
 */
@Component
public class PreParamCheckHandler implements Handler<SendTaskModel> {

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        //没有传入 消息模板Id 或者 messageParam
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        if (Objects.isNull(messageTemplateId) || CollUtil.isEmpty(messageParamList)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }
        //过滤 Receiver 为空的 messageParam
        List<MessageParam> resMessageParamList = messageParamList.stream()
                .filter(messageParam -> StrUtil.isNotBlank(messageParam.getReceiver()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(resMessageParamList)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }
        //过滤receiver大于100的请求
        for (MessageParam messageParam : resMessageParamList) {
            String receivers = messageParam.getReceiver();
            if (receivers.split(StrUtil.COMMA).length > LuminateConstant.BATCH_RECEIVER_SIZE) {
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TOO_MANY_RECEIVER));
            }
        }
        sendTaskModel.setMessageParamList(resMessageParamList);
    }
}
