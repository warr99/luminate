package com.warrior.luminate.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.warrior.luminate.domain.SmsRecord;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.SmsParam;
import com.warrior.luminate.dto.SmsContentModel;
import com.warrior.luminate.enums.ChannelTypeEnums;
import com.warrior.luminate.script.AliyunSmsScript;
import com.warrior.luminate.service.SmsRecordService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class SmsHandler extends AbstractHandler {

    private final AliyunSmsScript aliyunSmsScript;
    private final SmsRecordService smsRecordService;

    public SmsHandler(AliyunSmsScript aliyunSmsScript, SmsRecordService smsRecordService) {
        channelCode = ChannelTypeEnums.SMS.getCode();
        this.aliyunSmsScript = aliyunSmsScript;
        this.smsRecordService = smsRecordService;
    }


    @Override
    public void handler(TaskInfo taskInfo) {
        System.out.println("taskInfo: " + taskInfo.toString());
        System.out.println("handler SMS");
//        TODO 处理sms短信
//        SmsParam smsParam = SmsParam.builder()
//                .phones(taskInfo.getReceiver())
//                .content(getSmsContent(taskInfo))
//                .messageTemplateId(taskInfo.getMessageTemplateId())
//                .supplierId(10)
//                .supplierName("阿里云通知类消息渠道").build();
//        List<SmsRecord> recordList = aliyunSmsScript.send(smsParam);
//        if (!CollUtil.isEmpty(recordList)) {
//            smsRecordService.saveBatch(recordList);
//        }
    }


    private String getSmsContent(TaskInfo taskInfo) {
        SmsContentModel smsContentModel = (SmsContentModel) taskInfo.getContentModel();
        if (StrUtil.isNotBlank(smsContentModel.getUrl())) {
            return smsContentModel.getContent() + " " + smsContentModel.getUrl();
        } else {
            return smsContentModel.getContent();
        }
    }
}
