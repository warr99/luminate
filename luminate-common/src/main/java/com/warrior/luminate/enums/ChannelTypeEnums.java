package com.warrior.luminate.enums;

import com.warrior.luminate.dto.ContentModel;
import com.warrior.luminate.dto.SmsContentModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Getter
@ToString
@AllArgsConstructor
public enum ChannelTypeEnums {
    /**
     * sms 短信
     */
    SMS(30, "sms(短信)", SmsContentModel.class, "sms");
    /**
     * 编码值
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 内容模型Class
     */
    private final Class<? extends ContentModel> contentModelClass;

    /**
     * 英文标识
     */
    private final String code_en;

    public static Class<? extends ContentModel> getChannelModelClassByCode(Integer code) {
        ChannelTypeEnums[] values = values();
        for (ChannelTypeEnums value : values) {
            if (value.getCode().equals(code)) {
                return value.getContentModelClass();
            }
        }
        return null;
    }


}
