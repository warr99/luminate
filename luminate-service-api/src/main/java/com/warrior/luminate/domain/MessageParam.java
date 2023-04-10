package com.warrior.luminate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * @author WARRIOR
 * 消息参数
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageParam {

    /**
     * @description : 接收者
     * 多个用,逗号号分隔开(不能大于100个)
     * 必传
     */
    private String receiver;

    /**
     * @description : 消息内容中的可变部分(占位符替换)
     * 可选
     */
    private Map<String, String> variables;

    /**
     * @description : 扩展参数
     * 可选
     */
    private Map<String, String> extra;
}
