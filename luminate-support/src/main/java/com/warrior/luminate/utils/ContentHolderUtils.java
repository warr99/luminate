package com.warrior.luminate.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Map;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class ContentHolderUtils {

    /**
     * 占位符前缀
     */
    private static final String PLACE_HOLDER_PREFIX = "{$";

    /**
     * 占位符后缀
     */
    private static final String PLACE_HOLDER_SUFFIX = "}";

    /**
     * StandardEvaluationContext类:创建表达式求值的上下文环境
     */
    private static final StandardEvaluationContext EVALUATION_CONTEXT;

    /**
     * PropertyPlaceholderHelper 用于替换字符串模板中的占位符
     */
    private static final PropertyPlaceholderHelper PROPERTY_PLACEHOLDER_HELPER =
            new PropertyPlaceholderHelper(PLACE_HOLDER_PREFIX, PLACE_HOLDER_SUFFIX);

    static {
        //创建一个StandardEvaluationContext对象
        EVALUATION_CONTEXT = new StandardEvaluationContext();
        //并添加了一个MapAccessor对象作为属性访问器,被用于支持SpEL表达式在运行时访问Map类型的对象，从而可以更方便地实现字符串模板中的占位符替换
        EVALUATION_CONTEXT.addPropertyAccessor(new MapAccessor());
    }

    public static String replacePlaceHolder(String originValue, Map<String, String> variables) {
        return PROPERTY_PLACEHOLDER_HELPER.replacePlaceholders(originValue,
                new CustomPlaceholderResolver(variables));
    }

    /**
     * CustomPlaceholderResolver类是一个实现了PropertyPlaceholderHelper.PlaceholderResolver接口的内部类，
     * 用于实现占位符到参数值的映射关系。resolvePlaceholder方法用于根据占位符名称查找对应的参数值，并返回该值。
     * 如果找不到对应的参数值，则抛出IllegalArgumentException异常。
     */
    private static class CustomPlaceholderResolver implements PropertyPlaceholderHelper.PlaceholderResolver {
        private final Map<String, String> paramMap;

        public CustomPlaceholderResolver(Map<String, String> paramMap) {
            super();
            this.paramMap = paramMap;
        }

        @Override
        public String resolvePlaceholder(@NotNull String placeholderName) {
            String value = paramMap.get(placeholderName);
            if (value == null) {
                String errorStr = "require param: {" + placeholderName + "}, but not exist! param map: " + paramMap.toString();
                throw new IllegalArgumentException(errorStr);
            }
            return value;
        }

    }
}
