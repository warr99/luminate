package com.warrior.luminate.handler;


import cn.hutool.core.util.ReferenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.warrior.luminate.constant.CommonConstant;
import com.warrior.luminate.domain.MessageParam;
import com.warrior.luminate.domain.MessageTemplate;
import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domain.enums.RespStatusEnum;
import com.warrior.luminate.dto.ContentModel;
import com.warrior.luminate.enums.BusinessCode;
import com.warrior.luminate.enums.ChannelTypeEnums;
import com.warrior.luminate.pipeline.Handler;
import com.warrior.luminate.pipeline.ProcessContext;
import com.warrior.luminate.service.MessageTemplateService;
import com.warrior.luminate.utils.ContentHolderUtil;
import com.warrior.luminate.vo.BasicResultVO;
import org.springframework.stereotype.Component;


import java.lang.reflect.Field;
import java.util.*;

/**
 * @author WARRIOR
 * @version 1.0
 * 组装参数
 */

@Component
public class AssembleHandler implements Handler<SendTaskModel> {

    private final MessageTemplateService messageTemplateService;

    public AssembleHandler(MessageTemplateService messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        MessageTemplate messageTemplate = messageTemplateService.getById(messageTemplateId);
        if (messageTemplate == null || messageTemplate.getIsDeleted().equals(CommonConstant.TRUE)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TEMPLATE_NOT_FOUND));
            return;
        }
        try {
            if (BusinessCode.COMMON_SEND.getCode().equals(context.getCode())) {
                //普通发送
                List<TaskInfo> taskInfoList = assembleTaskInfo(sendTaskModel, messageTemplate);
                sendTaskModel.setTaskInfo(taskInfoList);
            } else if (BusinessCode.RECALL.getCode().equals(context.getCode())) {
                //TODO 撤回信息
                System.out.println("处理撤回消息");
            }
        } catch (Exception exception) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
        }

    }

    private List<TaskInfo> assembleTaskInfo(SendTaskModel sendTaskModel, MessageTemplate messageTemplate) {
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        List<TaskInfo> taskInfoList = new ArrayList<>();
        for (MessageParam messageParam : messageParamList) {
            TaskInfo taskInfo = TaskInfo.builder()
                    .messageTemplateId(messageTemplate.getId())
                    .businessId(null)
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(StrUtil.COMMA))))
                    .idType(messageTemplate.getIdType())
                    .sendChannel(messageTemplate.getSendChannel())
                    .templateType(messageTemplate.getTemplateType())
                    .msgType(messageTemplate.getMsgType())
                    .sendAccount(messageTemplate.getSendAccount())
                    //组装数据库中的消息模板和用户传入的参数
                    .contentModel(assembleContentModel(messageTemplate, messageParam))
                    .build();
            taskInfoList.add(taskInfo);
        }
        return taskInfoList;
    }

    private ContentModel assembleContentModel(MessageTemplate messageTemplate, MessageParam messageParam) {
        Integer sendChannelCode = messageTemplate.getSendChannel();
        Class<? extends ContentModel> modelClass = ChannelTypeEnums.getChannelModelClassByCode(sendChannelCode);
        // 用户入参
        Map<String, String> variables = messageParam.getVariables();
        // 数据库中模板的消息内容(JSON)
        JSONObject templateMsgContentJson = JSON.parseObject(messageTemplate.getMsgContent());

        Field[] fields = ReflectUtil.getFields(modelClass);
        //使用反射 根据消息类型模板(sms短信模板|email邮件模板... 组装当前消息类型的 contentModel)
        ContentModel contentModel = ReflectUtil.newInstance(modelClass);
        for (Field field : fields) {
            //根据当前模板的属性找到数据库模板消息内容中对应的内容,赋值
            String fieldName = field.getName();
            String originValue = templateMsgContentJson.getString(fieldName);
            if (StrUtil.isNotBlank(originValue)) {
                //如果数据库中的内容有占位符(${}),进行替换
                String resultValue = ContentHolderUtil.replacePlaceHolder(originValue, variables);
                ReflectUtil.setFieldValue(contentModel, field, resultValue);
            }
        }
        return contentModel;
    }
}
