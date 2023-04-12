package com.warrior.luminate.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.warrior.luminate.domain.SmsRecord;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.SmsParam;
import com.warrior.luminate.dto.SmsContentModel;
import com.warrior.luminate.enums.ChannelTypeEnums;
import com.warrior.luminate.script.AliyunSmsScript;
import com.warrior.luminate.service.SmsRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
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
    public boolean handler(TaskInfo taskInfo) {
        log.info("SmsHandler#handler taskInfo:{}", JSON.toJSONString(taskInfo));
        return true;
//        TODO 处理sms短信
//        SmsParam smsParam = SmsParam.builder()
//                .phones(taskInfo.getReceiver())
//                .content(getSmsContent(taskInfo))
//                .messageTemplateId(taskInfo.getMessageTemplateId())
//                .supplierId(10)
//                .supplierName("阿里云通知类消息渠道").build();
//        try {
//            List<SmsRecord> recordList = aliyunSmsScript.send(smsParam);
//            if (!CollUtil.isEmpty(recordList)) {
//                smsRecordService.saveBatch(recordList);
//            }
//            return true;
//        } catch (Exception e) {
//            log.error("SmsHandler#handler fail:{},params:{}",
//                    Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
//        }
//        return false;

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
