package com.warrior.luminate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author WARRIOR
 * @version 1.0
 * 消息链路追踪 -> 锚点状态枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum AnchorStateEnums {
    /**
     * 消息被成功消费
     */
    RECEIVE(10, "消息被成功消费"),
    /**
     * 消费被丢弃
     */
    DISCARD(20, "消费被丢弃"),
    /**
     * 消息被内容去重
     */
    CONTENT_DEDUPLICATION(30, "消息被内容去重"),
    /**
     * 消息被频次去重
     */
    RULE_DEDUPLICATION(40, "消息被频次去重"),
    /**
     * 下发成功（调用渠道接口成功）
     */
    SEND_SUCCESS(50, "消息下发成功"),
    /**
     * 下发失败（调用渠道接口失败）
     */
    SEND_FAIL(60, "消息下发失败");

    private final Integer code;
    private final String description;
}
