package com.warrior.luminate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.warrior.luminate.domain.MessageTemplate;
import com.warrior.luminate.mapper.MessageTemplateMapper;
import com.warrior.luminate.pending.BatchSendTaskDelayedProcess;
import com.warrior.luminate.service.TaskHandlerService;
import com.warrior.luminate.utils.ReadFileUtils;
import com.warrior.luminate.vo.CrowdInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
@Service
public class TaskHandlerServiceImpl implements TaskHandlerService {
    private final MessageTemplateMapper messageTemplateMapper;
    private final ApplicationContext context;

    @Autowired
    public TaskHandlerServiceImpl(MessageTemplateMapper messageTemplateMapper, ApplicationContext context) {
        this.messageTemplateMapper = messageTemplateMapper;
        this.context = context;
    }

    /**
     * 异步处理定时任务
     * @param messageTemplateId 消息模板id
     */
    @Override
    @Async
    public void handle(Long messageTemplateId) {
        MessageTemplate messageTemplate = messageTemplateMapper.selectById(messageTemplateId);
        if (messageTemplate == null) {
            log.error("TaskHandler#handle messageTemplate empty! messageTemplateId:{}", messageTemplateId);
            return;
        }
        String cronCrowdPath = messageTemplate.getCronCrowdPath();
        if (StrUtil.isBlank(cronCrowdPath)) {
            log.error("TaskHandler#handle crowdPath empty! messageTemplateId:{}", messageTemplateId);
            return;
        }
        //读取人群文件(按行读取)
        ReadFileUtils.getCsvRow(cronCrowdPath, row -> {
            //如果获取到的行数据中,userId为空,文件已经读完,返回
            if (CollUtil.isEmpty(row.getFieldMap())
                    || StrUtil.isBlank(row.getFieldMap().get(ReadFileUtils.RECEIVER_KEY))) {
                return;
            }
            //将行数据转化为HashMap参数
            HashMap<String, String> paramMap = ReadFileUtils.getParamFromLine(row.getFieldMap());
            //将参数和接收者封装为CrowdInfoVo
            CrowdInfoVo crowdInfoVo = CrowdInfoVo
                    .builder()
                    .messageTemplateId(messageTemplateId)
                    .receiver(row.getFieldMap().get(ReadFileUtils.RECEIVER_KEY))
                    .paramMap(paramMap).build();
            //将CrowdInfoVo丢给阻塞队列
            BatchSendTaskDelayedProcess batchSendTaskDelayedProcess = context.getBean(BatchSendTaskDelayedProcess.class);
            batchSendTaskDelayedProcess.putIntoQueue(crowdInfoVo);
        });

    }
}
