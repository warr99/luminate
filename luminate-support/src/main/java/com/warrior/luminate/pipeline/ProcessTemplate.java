package com.warrior.luminate.pipeline;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Getter
@Setter
public class ProcessTemplate <T extends ProcessModel>{
    /**
     * com.warrior.luminate.handler 链
     */
    private List<Handler<T>> handlerList;
}
