package com.warrior.luminate.domian;

import com.warrior.luminate.domain.TaskInfo;
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
    private Long deduplicationTime;

    /**
     * 达到countNum后去重
     */
    private Integer countNum;
}
