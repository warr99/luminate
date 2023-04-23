package com.warrior.luminate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 模板枚举信息
 *
 * @author 3y
 */
@Getter
@ToString
@AllArgsConstructor
public enum TemplateTypeEnums {

    /**
     * 10.运营类
     */
    CLOCKING(10, "定时类的模板(后台定时调用)"),
    /**
     * 20.技术类接口调用
     */
    REALTIME(20, "实时类的模板(接口实时调用)"),
    ;

    private final Integer code;
    private final String description;

}
