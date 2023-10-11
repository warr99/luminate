package com.warrior.luminate.xxl.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Throwables;
import com.warrior.luminate.enums.RespStatusEnum;
import com.warrior.luminate.vo.BasicResultVO;
import com.warrior.luminate.xxl.constants.XxlJobConstant;
import com.warrior.luminate.xxl.entity.XxlJobGroup;
import com.warrior.luminate.xxl.entity.XxlJobInfo;
import com.warrior.luminate.xxl.service.CronTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
@Service
public class CronTaskServiceImpl implements CronTaskService {
    @Value("${xxl.job.admin.username}")
    private String xxlUserName;

    @Value("${xxl.job.admin.password}")
    private String xxlPassword;

    @Value("${xxl.job.admin.addresses}")
    private String xxlAddresses;

    @Override
    public BasicResultVO<?> saveCronTask(XxlJobInfo xxlJobInfo) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(xxlJobInfo), HashMap.class);

        String path;
        if (xxlJobInfo.getId() == null) {
            path = xxlAddresses + XxlJobConstant.INSERT_URL;
        } else {
            path = xxlAddresses + XxlJobConstant.UPDATE_URL;
        }

        ReturnT returnT = null;
        try {
            HttpResponse response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                if (path.contains(XxlJobConstant.INSERT_URL)) {
                    Integer taskId = Integer.parseInt(String.valueOf(returnT.getContent()));
                    return BasicResultVO.successWithData(taskId);
                } else if (path.contains(XxlJobConstant.UPDATE_URL)) {
                    return BasicResultVO.success();
                }
            }
        } catch (Exception e) {
            log.error("CronTaskService#saveTask fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(xxlJobInfo), JSON.toJSONString(returnT));
        }
        return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR, returnT == null ? null : returnT.getMsg());
    }

    @Override
    public BasicResultVO<?> deleteCronTask(Integer taskId) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", taskId);
        String path = xxlAddresses + XxlJobConstant.DELETE_URL;
        HttpResponse response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
        if (!response.isOk()) {
            log.error("TaskService#deleteCronTask fail:{}", JSON.toJSONString(response.body()));
            return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(response.body()));
        }
        return BasicResultVO.success();
    }

    @Override
    public BasicResultVO<?> startCronTask(Integer taskId) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", taskId);
        String path = xxlAddresses + XxlJobConstant.RUN_URL;
        HttpResponse response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
        if (!response.isOk()) {
            log.error("TaskService#startCronTask fail:{}", JSON.toJSONString(response.body()));
            return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(response.body()));
        }
        log.info("TaskService#startCronTask success:{}", JSON.toJSONString(response.body()));
        return BasicResultVO.success();
    }

    @Override
    public BasicResultVO<?> stopCronTask(Integer taskId) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", taskId);
        String path = xxlAddresses + XxlJobConstant.STOP_URL;
        HttpResponse response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
        if (!response.isOk()) {
            log.error("TaskService#stopCronTask fail:{}", JSON.parseObject(response.body()));
            return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(response.body()));
        }
        return BasicResultVO.success();
    }

    @Override
    public BasicResultVO<?> getGroupId(String appName, String title) {
        String path = xxlAddresses + XxlJobConstant.JOB_GROUP_PAGE_LIST;

        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("appname", appName);
        params.put("title", title);

        HttpResponse response = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            JSONArray data = JSON.parseObject(response.body()).getJSONArray("data");
            if (data == null) {
                return null;
            }
            Integer id = JSON.parseObject(response.body()).getJSONArray("data").getJSONObject(0).getInteger("id");
            if (response.isOk() && Objects.nonNull(id)) {
                return BasicResultVO.successWithData(id);
            }
        } catch (Exception e) {
            log.error("CronTaskService#getGroupId fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(response != null ? response.body() : null));
        }
        return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(response != null ? response.body() : null));
    }

    @Override
    public BasicResultVO<?> createGroup(XxlJobGroup xxlJobGroup) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(xxlJobGroup), Map.class);
        String path = xxlAddresses + XxlJobConstant.JOB_GROUP_INSERT_URL;

        HttpResponse response;
        ReturnT returnT = null;

        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BasicResultVO.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#createGroup fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(returnT));
    }

    private String getCookie() {
        String path = xxlAddresses + XxlJobConstant.LOGIN_URL;
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("userName", xxlUserName);
        hashMap.put("password", xxlPassword);
        log.info("TaskService#getCookie params：{}", hashMap);

        HttpResponse response = HttpRequest.post(path).form(hashMap).execute();
        if (response.isOk()) {
            List<HttpCookie> cookies = response.getCookies();
            StringBuilder sb = new StringBuilder();
            for (HttpCookie cookie : cookies) {
                sb.append(cookie.toString());
            }
            return sb.toString();
        }
        log.error("TaskService#getCookie fail:{}", JSON.parseObject(response.body()));
        return null;
    }
}
