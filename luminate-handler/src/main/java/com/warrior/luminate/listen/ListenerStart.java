package com.warrior.luminate.listen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;
import com.warrior.luminate.utils.GroupIdUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Service
public class ListenerStart {
    private final ApplicationContext context;

    /**
     * receiver的消费方法名字
     */
    private static final String RECEIVER_METHOD_NAME = "Listener.consumer";


    /**
     * 获取得到所有的groupId
     */
    private static final List<String> GROUP_IDS = GroupIdUtils.getAllGroupId();

    /**
     * 下标(用于迭代groupIds位置)
     */
    private static Integer index = 0;

    @Autowired
    public ListenerStart(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 为每个渠道不同的消息类型 创建一个Receiver对象
     */
    @PostConstruct
    public void init() {
        for (int i = 0; i < GROUP_IDS.size(); i++) {
            context.getBean(Listener.class);
        }
    }

    /**
     * 给每个Receiver对象的consumer方法 @KafkaListener赋值相应的groupId
     */
    @Bean
    public static KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer groupIdEnhancer() {
        return (attrs, element) -> {
            //如果是一个方法
            if (element instanceof Method) {
                String name = ((Method) element).getDeclaringClass().getSimpleName() + "." + ((Method) element).getName();
                //如果这个方法的名称为"Listener.consumer"
                if (RECEIVER_METHOD_NAME.equals(name)) {
                    attrs.put("groupId", GROUP_IDS.get(index));
                    index++;
                }
            }
            return attrs;
        };
    }

}
