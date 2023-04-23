package com.warrior.luminate.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warrior.luminate.constant.CommonConstant;
import com.warrior.luminate.constant.LuminateConstant;
import com.warrior.luminate.domain.MessageTemplate;
import com.warrior.luminate.entity.XxlJobInfo;
import com.warrior.luminate.enums.AuditStatusEnums;
import com.warrior.luminate.enums.MessageStatusEnums;
import com.warrior.luminate.enums.RespStatusEnum;
import com.warrior.luminate.enums.TemplateTypeEnums;
import com.warrior.luminate.mapper.MessageTemplateMapper;
import com.warrior.luminate.service.CronTaskService;
import com.warrior.luminate.utils.XxlJobUtil;
import com.warrior.luminate.vo.BasicResultVO;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author WARRIOR
 * @description 针对表【message_template(消息模板信息)】的数据库操作Service实现
 * @createDate 2023-04-03 14:14:00
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplate>
        implements MessageTemplateService {

    private final CronTaskService cronTaskService;
    private final XxlJobUtil xxlJobUtil;

    public MessageTemplateServiceImpl(CronTaskService cronTaskService, XxlJobUtil xxlJobUtil) {
        this.cronTaskService = cronTaskService;
        this.xxlJobUtil = xxlJobUtil;
    }

    @Override
    public boolean saveOrUpdate(MessageTemplate messageTemplate) {
        if (Objects.isNull(messageTemplate.getId())) {
            initStatus(messageTemplate);
        } else {
            resetStatus(messageTemplate);
        }
        messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        return super.saveOrUpdate(messageTemplate);
    }

    @Override
    public BasicResultVO<?> startCronTask(Long id) {
        MessageTemplate messageTemplate = getById(id);
        if (messageTemplate == null) {
            return BasicResultVO.fail("该模板不存在");
        }
        Integer cronTaskId = messageTemplate.getCronTaskId();
        if (cronTaskId == null) {
            //定时任务未创建->创建定时任务,返回taskId
            XxlJobInfo xxlJobInfo = xxlJobUtil.buildXxlJobInfo(messageTemplate);
            BasicResultVO<?> basicResultVO = cronTaskService.saveCronTask(xxlJobInfo);
            if (RespStatusEnum.SUCCESS.getCode().equals(basicResultVO.getCode()) && ObjectUtil.isNotNull(basicResultVO.getData())) {
                cronTaskId = Integer.valueOf(String.valueOf(basicResultVO.getData()));
            }
        }
        //修改模板状态
        messageTemplate.setMsgStatus(MessageStatusEnums.RUN.getCode());
        messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        messageTemplate.setCronTaskId(cronTaskId);
        updateById(messageTemplate);
        //执行定时任务
        return cronTaskService.startCronTask(cronTaskId);
    }

    @Override
    public BasicResultVO<?> stopCronTask(Long id) {
        // 1.修改模板状态
        MessageTemplate messageTemplate = getById(id);
        if (Objects.isNull(messageTemplate)) {
            return BasicResultVO.fail();
        }
        messageTemplate.setMsgStatus(MessageStatusEnums.STOP.getCode());
        messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        updateById(messageTemplate);
        // 2.暂停定时任务
        return cronTaskService.stopCronTask(messageTemplate.getCronTaskId());
    }

    /**
     * 初始化状态信息
     *
     * @param messageTemplate messageTemplate
     */
    private void initStatus(MessageTemplate messageTemplate) {
        messageTemplate.setFlowId(StrUtil.EMPTY)
                .setMsgStatus(MessageStatusEnums.INIT.getCode()).setAuditStatus(AuditStatusEnums.AUDIT_SUCCESS.getCode())
                .setCreator(StrUtil.isBlank(messageTemplate.getCreator()) ? LuminateConstant.DEFAULT_CREATOR : messageTemplate.getCreator())
                .setUpdator(StrUtil.isBlank(messageTemplate.getUpdator()) ? LuminateConstant.DEFAULT_UPDATOR : messageTemplate.getUpdator())
                .setTeam(StrUtil.isBlank(messageTemplate.getTeam()) ? LuminateConstant.DEFAULT_TEAM : messageTemplate.getTeam())
                .setAuditor(StrUtil.isBlank(messageTemplate.getAuditor()) ? LuminateConstant.DEFAULT_AUDITOR : messageTemplate.getAuditor())
                .setCreated(Math.toIntExact(DateUtil.currentSeconds()))
                .setIsDeleted(CommonConstant.FALSE);

    }

    /**
     * 重置状态信息
     *
     * @param messageTemplate messageTemplate
     */
    private void resetStatus(MessageTemplate messageTemplate) {
        //重置模板的状态
        messageTemplate.setUpdator(messageTemplate.getUpdator())
                .setMsgStatus(MessageStatusEnums.INIT.getCode()).setAuditStatus(AuditStatusEnums.AUDIT_SUCCESS.getCode());
        //修改定时任务信息(如果存在)
        if (Objects.nonNull(messageTemplate.getCronTaskId()) && TemplateTypeEnums.CLOCKING.getCode().equals(messageTemplate.getTemplateType())) {
            XxlJobInfo xxlJobInfo = xxlJobUtil.buildXxlJobInfo(messageTemplate);
            cronTaskService.saveCronTask(xxlJobInfo);
            //cronTaskService.stopCronTask(messageTemplate.getCronTaskId());
        }
    }
}




