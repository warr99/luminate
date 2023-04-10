package com.warrior.luminate.handler;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.ssh.ChannelType;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domain.enums.RespStatusEnum;
import com.warrior.luminate.enums.ChannelTypeEnums;
import com.warrior.luminate.enums.IdTypeEnums;
import com.warrior.luminate.pipeline.Handler;
import com.warrior.luminate.pipeline.ProcessContext;
import com.warrior.luminate.vo.BasicResultVO;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author WARRIOR
 * @version 1.0
 * 后置检查
 */
@Component
public class AfterParamCheckHandle implements Handler<SendTaskModel> {
    public static final String PHONE_REGEX_EXP = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        List<TaskInfo> taskInfoList = sendTaskModel.getTaskInfo();

        //过滤掉不合法的手机号
        filterIllegalPhoneNum(taskInfoList);
        //如果过滤后任务列表为空,返回,不用发送信息
        if (CollUtil.isEmpty(taskInfoList)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
        }
    }

    /**
     * 如果指定类型是手机号，且渠道是发送短信，检测输入手机号是否合法
     *
     * @param taskInfoList 消息任务信息列表
     */
    private void filterIllegalPhoneNum(List<TaskInfo> taskInfoList) {
        //先找出id类型
        Integer idType = taskInfoList.get(0).getIdType();
        //发送渠道
        Integer sendChannel = taskInfoList.get(0).getSendChannel();
        //如果指定类型是手机号，且渠道是发送短信，检测输入手机号是否合法
        if (IdTypeEnums.PHONE.getCode().equals(idType) && ChannelTypeEnums.SMS.getCode().equals(sendChannel)) {
            Iterator<TaskInfo> iterator = taskInfoList.iterator();
            // 利用正则找出不合法的手机号
            while (iterator.hasNext()) {
                TaskInfo task = iterator.next();
                Set<String> illegalPhone = task.getReceiver().stream()
                        .filter(phone -> !ReUtil.isMatch(PHONE_REGEX_EXP, phone))
                        .collect(Collectors.toSet());
                //从一个taskInfo中移除不合法的手机
                if (CollUtil.isNotEmpty(illegalPhone)) {
                    task.getReceiver().removeAll(illegalPhone);
                }
                //如果移除后receiver为空,直接把该taskInfo从列表中移除
                if (CollUtil.isEmpty(task.getReceiver())) {
                    iterator.remove();
                }
            }
        }
    }
}
