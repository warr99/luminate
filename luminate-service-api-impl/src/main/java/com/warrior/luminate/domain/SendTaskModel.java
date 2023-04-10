package com.warrior.luminate.domain;


import com.warrior.luminate.pipeline.ProcessModel;
import lombok.*;

import java.util.List;


/**
 * @author WARRIOR
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SendTaskModel implements ProcessModel {

    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务的信息
     */
    private List<TaskInfo> taskInfo;


}
