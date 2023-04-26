package com.warrior.luminate.domian;

import com.alibaba.fastjson.annotation.JSONField;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.enums.AnchorStateEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WARRIOR
 * @version 1.0
 * 去重需要的参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeduplicationParam {
    /**
     * TaskIno信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间
     * 单位：秒
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需达到的次数去重
     */
    @JSONField(name = "num")
    private Integer countNum;

    /**
     * 标识属于哪种去重(数据埋点)
     */
    private AnchorStateEnums anchorState;
}
