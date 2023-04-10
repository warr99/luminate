package com.warrior.luminate.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Component
public class HandlerHolder {
    private final Map<Integer, AbstractHandler> handlers = new HashMap<>(32);

    public void putHandler(Integer channelCode, AbstractHandler handler) {
        handlers.put(channelCode, handler);
    }

    public AbstractHandler getHandler(Integer channelCode) {
        return handlers.get(channelCode);
    }

}
