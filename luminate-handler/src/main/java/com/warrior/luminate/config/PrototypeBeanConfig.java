package com.warrior.luminate.config;


import com.warrior.luminate.listen.Listener;
import com.warrior.luminate.pending.Task;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Handler模块的配置信息
 *
 * @author warrior
 */
@Configuration
public class PrototypeBeanConfig {

    /**
     * 定义多例的 Listener
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Listener listener() {
        return new Listener();
    }

    /**
     * 定义多例的 Task
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Task task() {
        return new Task();
    }

}
