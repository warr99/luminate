package com.warrior.luminate.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.dto.SmsContentModel;
import com.warrior.luminate.enums.ChannelTypeEnums;
import com.warrior.luminate.mapper.SmsRecordMapper;
import com.warrior.luminate.script.AliyunSmsScript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
@Component
public class SmsHandler extends AbstractHandler {

    private final AliyunSmsScript aliyunSmsScript;
    private final SmsRecordMapper smsRecordMapper;

    @Autowired
    public SmsHandler(AliyunSmsScript aliyunSmsScript, SmsRecordMapper smsRecordMapper) {
        channelCode = ChannelTypeEnums.SMS.getCode();
        this.aliyunSmsScript = aliyunSmsScript;
        this.smsRecordMapper = smsRecordMapper;
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
