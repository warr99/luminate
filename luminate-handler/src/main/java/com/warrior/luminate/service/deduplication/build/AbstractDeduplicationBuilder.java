package com.warrior.luminate.service.deduplication.build;

import com.alibaba.fastjson.JSONObject;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.service.deduplication.DeduplicationHolder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author WARRIOR
 */
public abstract class AbstractDeduplicationBuilder implements Builder {

    protected Integer deduplicationType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @PostConstruct
    public void init() {
        deduplicationHolder.putBuilder(deduplicationType, this);
    }

    public DeduplicationParam getParamsFromConfig(Integer key, String duplicationConfig, TaskInfo taskInfo) {
        JSONObject object = JSONObject.parseObject(duplicationConfig);
        if (object == null) {
            return null;
        }
        DeduplicationParam deduplicationParam = JSONObject.parseObject(object.getString(CONFIG_PRE + key), DeduplicationParam.class);
        if (deduplicationParam == null) {
            return null;
        }
        deduplicationParam.setTaskInfo(taskInfo);
        return deduplicationParam;
    }

}
