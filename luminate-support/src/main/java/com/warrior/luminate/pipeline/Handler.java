package com.warrior.luminate.pipeline;


/**
 * @author WARRIOR
 * @version 1.0
 */
public interface Handler<T extends ProcessModel> {
    /**
     * 责任链中每个handler的处理方法
     *
     * @param context 责任链上下文
     */
    void process(ProcessContext<T> context);
}
