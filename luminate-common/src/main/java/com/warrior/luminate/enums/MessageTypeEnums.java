package com.warrior.luminate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author WARRIOR
 * @version 1.0
 * 消息类型的枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageTypeEnums {
    /**
     * 通知类消息
     */
    NOTICE(10, "通知类消息", "notice"),

    /**
     * 营销类消息
     */
    MARKETING(20, "营销类消息", "marketing"),

    /**
     * 验证码消息
     */
    AUTH_CODE(30, "验证码消息", "verificationCode");

    /**
     * 编码值
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;


    /**
     * 英文标识
     */
    private final String EnCode;

    public static MessageTypeEnums getMessageTypeByCode(Integer code) {
        for (MessageTypeEnums messageType : MessageTypeEnums.values()) {
            if (messageType.getCode().equals(code)){
                return messageType;
            }
        }
        return null;
    }

}
