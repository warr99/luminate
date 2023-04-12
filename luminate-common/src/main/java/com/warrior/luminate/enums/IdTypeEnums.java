package com.warrior.luminate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author WARRIOR
 * @version 1.0
 * 发送id类型枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum IdTypeEnums {
    /**
     * 手机号码
     */
    PHONE(30, "phone"),
    EMAIL(40, "email");
    /**
     * 编码
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String description;
}
