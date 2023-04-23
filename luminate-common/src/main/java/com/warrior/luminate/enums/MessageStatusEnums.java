package com.warrior.luminate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 3y
 * @date 2022/1/22
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageStatusEnums {

    /**
     * 10.新建
     */
    INIT(10, "初始化状态"),
    /**
     * 20.停用
     */
    STOP(20, "停用"),
    /**
     * 30.启用
     */
    RUN(30, "启用");


    private final Integer code;
    private final String description;


}
