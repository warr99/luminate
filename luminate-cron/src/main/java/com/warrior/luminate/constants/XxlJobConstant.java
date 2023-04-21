package com.warrior.luminate.constants;


/**
 * xxl-job常量信息
 *
 * @author 3y
 */
public class XxlJobConstant {

    /**
     * 接口路径
     */
    public static final String LOGIN_URL = "/login";
    public static final String INSERT_URL = "/jobinfo/add";
    public static final String UPDATE_URL = "/jobinfo/update";
    public static final String DELETE_URL = "/jobinfo/remove";
    public static final String RUN_URL = "/jobinfo/start";
    public static final String STOP_URL = "/jobinfo/stop";

    /**
     * 执行器名称
     */
    public static final String HANDLER_NAME = "luminateJobHandler";

    /**
     * 超时时间
     */
    public static final Integer TIME_OUT = 120;

    /**
     * 失败重试次数
     */
    public static final Integer RETRY_COUNT = 2;

}
