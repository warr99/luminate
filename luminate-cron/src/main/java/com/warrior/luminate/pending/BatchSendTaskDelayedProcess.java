package com.warrior.luminate.pending;

import com.google.common.collect.Lists;
import com.warrior.luminate.config.CronAsyncThreadPoolConfig;
import com.warrior.luminate.constants.PendingConstant;
import com.warrior.luminate.domain.BatchSendRequest;
import com.warrior.luminate.domain.MessageParam;
import com.warrior.luminate.enums.BusinessCode;
import com.warrior.luminate.service.SendService;
import com.warrior.luminate.vo.CrowdInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
@Component
public class BatchSendTaskDelayedProcess extends AbstractDelayedProcess<CrowdInfoVo> {
    private final SendService sendService;

    @Autowired
    public BatchSendTaskDelayedProcess(SendService sendService) {
        PendingParam<CrowdInfoVo> pendingParam = new PendingParam<>();
        pendingParam.setNumThreshold(PendingConstant.NUM_THRESHOLD)
                .setBlockingDeque(new LinkedBlockingDeque<>(PendingConstant.QUEUE_SIZE))
                .setTimeThreshold(PendingConstant.TIME_THRESHOLD)
                .setExecutorService(CronAsyncThreadPoolConfig.getConsumePendingThreadPool());
        this.pendingParam = pendingParam;
        this.sendService = sendService;
    }

    @Override
    public void doHandle(List<CrowdInfoVo> pendingList) {
        Map<Map<String, String>, String> allReceiverParamMap = new HashMap<>(16);

        //遍历pendingList,将参数相同的receiver拼接在一起
        for (CrowdInfoVo crowdInfoVo : pendingList) {
            String receiver = crowdInfoVo.getReceiver();
            Map<String, String> oneReceiverParamMap = crowdInfoVo.getParamMap();
            if (allReceiverParamMap.get(oneReceiverParamMap) == null) {
                allReceiverParamMap.put(oneReceiverParamMap, receiver);
            } else {
                String newReceiver = allReceiverParamMap.get(oneReceiverParamMap) + "," + receiver;
                allReceiverParamMap.put(oneReceiverParamMap, newReceiver);
            }
        }
        //组装messageParamList
        List<MessageParam> messageParamList = Lists.newArrayList();
        for (Map.Entry<Map<String, String>, String> entry : allReceiverParamMap.entrySet()) {
            MessageParam messageParam = MessageParam
                    .builder()
                    .receiver(entry.getValue())
                    .variables(entry.getKey()).build();
            messageParamList.add(messageParam);
        }
        //调用批量发送接口
        sendService.batchSend(
                BatchSendRequest.builder()
                        .code(BusinessCode.COMMON_SEND.getCode())
                        .messageParamList(messageParamList)
                        .messageTemplateId(pendingList.get(0).getMessageTemplateId())
                        .build());
    }
}
