package com.warrior.luminate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author WARRIOR
 * @version 1.0
 * 锚点信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnchorInfo {
    /**
     * 业务Id:businessId会跟随着消息的生命周期始终,被用于消息的全链路追踪
     */
    private Long businessId;

    /**
     * 发送用户
     */
    private Set<String> ids;

    /**
     * 具体锚点信息(消息被成功消费|消费被丢弃...) -> AnchorStateEnums
     */
    private int state;


    /**
     * 日志生成时间
     */
    private long logTimestamp;
}
