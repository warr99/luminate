package com.warrior.luminate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 去重类型枚举
 *
 * @author WARRIOR
 */
@Getter
@ToString
@AllArgsConstructor
public enum DeduplicationTypeEnums {

    /**
     * N分钟相同内容去重
     */
    CONTENT(10, "N分钟相同内容去重"),
    /**
     * 一天内N次相同渠道去重
     */
    FREQUENCY(20, "一天内N次相同渠道去重"),
    ;
    private final Integer code;
    private final String description;


    /**
     * 获取去重渠道的列表
     *
     * @return 去重渠道的列表
     */
    public static List<Integer> getDeduplicationList() {
        ArrayList<Integer> result = new ArrayList<>();
        for (DeduplicationTypeEnums value : DeduplicationTypeEnums.values()) {
            result.add(value.getCode());
        }
        return result;
    }
}
