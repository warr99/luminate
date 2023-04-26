package com.warrior.luminate.utils;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.CustomLogListener;
import com.alibaba.fastjson.JSON;
import com.warrior.luminate.domain.AnchorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author WARRIOR
 * @version 1.0
 * 日志工具类
 */
@Slf4j
@Component
public class LogUtils extends CustomLogListener {
    /**
     * 方法切面的日志 @OperationLog 所产生
     */
    @Override
    public void createLog(LogDTO logDTO) throws Exception {
        log.info(JSON.toJSONString(logDTO));
    }

    public void recordAnchorLog(AnchorInfo anchorInfo) {
        anchorInfo.setLogTimestamp(System.currentTimeMillis());
        log.info(JSON.toJSONString(anchorInfo));
    }

}
