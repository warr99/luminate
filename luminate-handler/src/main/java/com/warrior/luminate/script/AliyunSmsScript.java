package com.warrior.luminate.script;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.common.base.Throwables;
import com.warrior.luminate.constant.LuminateConstant;
import com.warrior.luminate.domain.SmsRecord;
import com.warrior.luminate.domian.SmsParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * @author WARRIOR
 * <p>
 * * 使用AK&SK初始化账号Client
 * * @param accessKeyId
 * * @param accessKeySecret
 * * @return Client
 * * @throws Exception
 */
@Slf4j
@Component
public class AliyunSmsScript {
    @Value("${aliyun.sms.account.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.account.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.account.sign-name}")
    private String signName;

    @Value("${aliyun.sms.account.template-code}")
    private String templateCode;


    private static final String URL = "dysmsapi.aliyuncs.com";

    /**
     * 初始化client
     */
    public Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = URL;
        return new Client(config);
    }

    /**
     * 调用接口,发送短信
     *
     * @param smsParam smsParam
     */
    public List<SmsRecord> send(SmsParam smsParam) {
        try {
            Client client = createClient();
            SendSmsRequest sendSmsRequest = assembleReq(smsParam);
            RuntimeOptions runtime = new RuntimeOptions();
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            return assembleSmsRecord(smsParam, sendSmsResponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AliyunSmsScript#send fail!{},params:{}", Throwables.getStackTraceAsString(e), smsParam);
            return null;
        }

    }

    /**
     * 组装请求信息
     *
     * @param smsParam smsParam
     * @return SendSmsRequest
     */
    private SendSmsRequest assembleReq(SmsParam smsParam) {

        SendSmsRequest req = new SendSmsRequest();
        Set<String> phones = smsParam.getPhones();
        String phoneStr = "";
        phoneStr = String.join(",", phones);
        req.setSignName(signName);
        req.setTemplateCode(templateCode);
        req.setPhoneNumbers(phoneStr);
        return req;
        /*
        测试签名关联的模板无法添加参数,只有固定内容,因此无需设置TemplateParam
        String content = smsParam.getContent();
        JSONObject contentJson = new JSONObject();
        contentJson.put("content", content);
        req.setTemplateParam(contentJson.toJSONString());
         */
    }

    /**
     * 组装响应信息->SmsRecord
     *
     * @param smsParam smsParam
     * @param response response
     */
    private List<SmsRecord> assembleSmsRecord(SmsParam smsParam, SendSmsResponse response) {
        //TODO 调用短信发送的接口之后不能马上调用 querySendDetails() 查询发送信息,可能查不到 -> 消息队列异步执行
        List<SmsRecord> smsRecords = new ArrayList<>();
        try {
            String bizId = response.getBody().getBizId();
            Set<String> phones = smsParam.getPhones();
            for (String phone : phones) {
                QuerySendDetailsRequest queryReq = new QuerySendDetailsRequest()
                        .setPhoneNumber(com.aliyun.teautil.Common.assertAsString(phone))
                        .setBizId(bizId)
                        .setSendDate(DateUtil.format(new Date(), LuminateConstant.YYYY_MM_DD))
                        .setPageSize(10L)
                        .setCurrentPage(1L);
                Client client = createClient();
                QuerySendDetailsResponse querySendDetailsResponse = client.querySendDetails(queryReq);
                if (querySendDetailsResponse == null) {
                    break;
                }
                List<QuerySendDetailsResponseBody.QuerySendDetailsResponseBodySmsSendDetailDTOsSmsSendDetailDTO> smsSendDetailDTO = querySendDetailsResponse.body.smsSendDetailDTOs.smsSendDetailDTO;
                if (smsSendDetailDTO == null || smsSendDetailDTO.isEmpty()) {
                    break;
                }
                SmsRecord smsRecord = SmsRecord.builder()
                        .sendDate(Integer.valueOf(DateUtil.format(new Date(), LuminateConstant.YYYY_MM_DD)))
                        .messageTemplateId(smsParam.getMessageTemplateId())
                        .msgContent(smsParam.getContent())
                        .phone(Long.valueOf(phone))
                        .supplierId(smsParam.getSupplierId())
                        .supplierName(smsParam.getSupplierName())
                        .status(Math.toIntExact(smsSendDetailDTO.get(0).sendStatus))
                        .reportContent(smsSendDetailDTO.get(0).errCode)
                        .created(Math.toIntExact(DateUtil.currentSeconds()))
                        .updated(Math.toIntExact(DateUtil.currentSeconds()))
                        .build();
                smsRecords.add(smsRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return smsRecords;

    }
}



