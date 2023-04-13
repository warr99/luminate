package com.warrior.luminate.service.deduplication;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.domian.DeduplicationParam;
import com.warrior.luminate.enums.DeduplicationTypeEnums;
import com.warrior.luminate.service.deduplication.DeduplicationHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author WARRIOR
 * @version 1.0
 * 去重
 */
@Service
public class DeduplicationRuleService {


    private final DeduplicationHolder deduplicationHolder;


    @Autowired
    public DeduplicationRuleService(DeduplicationHolder deduplicationHolder) {

        this.deduplicationHolder = deduplicationHolder;
    }

    public void duplication(TaskInfo taskInfo) {
        //TODO bug
        //读取参数->暂时写死,后续可以写到配置中心,实现热部署等操作
        String configurableDeduplicationParam = "{\"deduplication_10\":{\"num\":1,\"time\":300},\"deduplication_20\":{\"num\":5}}";
        List<Integer> deduplicationList = DeduplicationTypeEnums.getDeduplicationList();
        for (Integer deduplicationCode : deduplicationList) {
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationCode).build(configurableDeduplicationParam, taskInfo);
            if (Objects.nonNull(deduplicationParam)) {
                deduplicationHolder.selectService(deduplicationCode).deduplication(deduplicationParam);
            }
        }
    }

}
