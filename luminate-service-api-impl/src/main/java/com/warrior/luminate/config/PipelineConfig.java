package com.warrior.luminate.config;


import com.warrior.luminate.domain.SendTaskModel;
import com.warrior.luminate.handler.AfterParamCheckHandle;
import com.warrior.luminate.handler.AssembleHandler;
import com.warrior.luminate.handler.PreParamCheckHandler;
import com.warrior.luminate.handler.SendMqHandler;
import com.warrior.luminate.pipeline.Handler;
import com.warrior.luminate.pipeline.ProcessController;
import com.warrior.luminate.pipeline.ProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Configuration
public class PipelineConfig {

    private final PreParamCheckHandler preParamCheckHandler;
    private final AssembleHandler assembleHandler;
    private final AfterParamCheckHandle afterParamCheckHandle;
    private final SendMqHandler sendMqHandler;

    @Autowired
    public PipelineConfig(PreParamCheckHandler preParamCheckHandler, AssembleHandler assembleHandler, AfterParamCheckHandle afterParamCheckHandle, SendMqHandler sendMqHandler) {
        this.preParamCheckHandler = preParamCheckHandler;
        this.assembleHandler = assembleHandler;
        this.afterParamCheckHandle = afterParamCheckHandle;
        this.sendMqHandler = sendMqHandler;
    }

    @Bean
    public ProcessController<SendTaskModel> processController() {
        ProcessController<SendTaskModel> processController = new ProcessController<>();
        processController.setProcessTemplate(processTemplate());
        return processController;
    }

    /**
     * 发送信息执行流程模板
     *
     * @return ProcessTemplate 实例化对象
     */
    public ProcessTemplate<SendTaskModel> processTemplate() {
        ProcessTemplate<SendTaskModel> processTemplate = new ProcessTemplate<>();
        ArrayList<Handler<SendTaskModel>> handlers = new ArrayList<>();
        processTemplate.setHandlerList(Arrays.asList(preParamCheckHandler, assembleHandler, afterParamCheckHandle, sendMqHandler));
        return processTemplate;
    }


}
