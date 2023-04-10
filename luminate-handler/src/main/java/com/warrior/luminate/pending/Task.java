package com.warrior.luminate.pending;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.handler.HandlerHolder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Data
@Accessors(chain = true)

public class Task implements Runnable{
    @Autowired
    private HandlerHolder handlerHolder;

    private TaskInfo taskInfo;

    @Override
    public void run() {
        handlerHolder
                .getHandler(taskInfo.getSendChannel())
                .handler(taskInfo);
    }
}
