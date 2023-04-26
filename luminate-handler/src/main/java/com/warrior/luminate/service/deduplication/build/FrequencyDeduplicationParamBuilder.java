package com.warrior.luminate.service.deduplication.build;


import cn.hutool.core.date.DateUtil;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.enums.AnchorStateEnums;
import com.warrior.luminate.enums.DeduplicationTypeEnums;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author WARRIOR
 * @version 1.0
 * 频次去重参数(DeduplicationParam)构建
 */
@Component
public class FrequencyDeduplicationParamBuilder extends AbstractDeduplicationBuilder implements Builder {
    public FrequencyDeduplicationParamBuilder() {
        deduplicationType = DeduplicationTypeEnums.FREQUENCY.getCode();
    }

    @Override
    public DeduplicationParam build(String configurableDeduplicationParam, TaskInfo taskInfo) {
        if (configurableDeduplicationParam == null) {
            return null;
        }
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, configurableDeduplicationParam, taskInfo);
        //过期时间 —> 从当前时间到当天结束时间的剩余秒数
        Long seconds = (DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000;
        deduplicationParam.setDeduplicationTime(seconds);
        deduplicationParam.setAnchorState(AnchorStateEnums.RULE_DEDUPLICATION);
        return deduplicationParam;
    }
}
