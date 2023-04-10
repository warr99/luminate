package com.warrior.luminate.pipeline;

import lombok.Setter;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Setter
public class ProcessController<T extends ProcessModel> {

    private ProcessTemplate<T> processTemplate;

    public ProcessContext<T> process(ProcessContext<T> context) {
        List<Handler<T>> handlerList = processTemplate.getHandlerList();
        for (Handler<T> handler : handlerList) {
            handler.process(context);
            if (context.getNeedBreak()) {
                break;
            }
        }
        return context;
    }
}
