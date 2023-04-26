package com.warrior.luminate.service.deduplication.build;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.enums.AnchorStateEnums;
import com.warrior.luminate.enums.DeduplicationTypeEnums;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class ContentDeduplicationParamBuilder extends AbstractDeduplicationBuilder implements Builder {
    public ContentDeduplicationParamBuilder() {
        deduplicationType = DeduplicationTypeEnums.CONTENT.getCode();
    }


    @Override
    public DeduplicationParam build(String configurableDeduplicationParam, TaskInfo taskInfo) {
        if (configurableDeduplicationParam == null) {
            return null;
        }
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, configurableDeduplicationParam, taskInfo);
        deduplicationParam.setAnchorState(AnchorStateEnums.CONTENT_DEDUPLICATION);
        return deduplicationParam;

    }
}
