package com.warrior.luminate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsContentModel implements ContentModel {

    /**
     * 短信发送内容
     */
    private String content;

    /**
     * 短信发送链接
     */
    private String url;

}
