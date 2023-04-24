package com.warrior.luminate.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.warrior.luminate.domain.MessageTemplate;
import com.warrior.luminate.enums.RespStatusEnum;
import com.warrior.luminate.service.MessageTemplateService;
import com.warrior.luminate.vo.BasicResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Slf4j
@Api(value = "MessageTemplateController", tags = {"模板接口"})
@RestController
public class MessageTemplateController {
    @Value("${luminate.upload.file.path}")
    private String path;
    private final MessageTemplateService messageTemplateService;

    @Autowired
    public MessageTemplateController(MessageTemplateService messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }

    /**
     * 如果Id存在，则修改
     * 如果Id不存在，则保存
     */
    @PostMapping("/save")
    @ApiOperation("/保存数据")
    public BasicResultVO<Boolean> saveOrUpdate(@RequestBody MessageTemplate messageTemplate) {
        boolean isSuccess = messageTemplateService.saveOrUpdate(messageTemplate);
        return BasicResultVO.successWithData(isSuccess);
    }

    /**
     * 根据Id查找
     */
    @GetMapping("query/{id}")
    @ApiOperation("/根据Id查找")
    public BasicResultVO<MessageTemplate> queryById(@PathVariable("id") Long id) {
        MessageTemplate messageTemplate = messageTemplateService.getById(id);
        return BasicResultVO.successWithData(messageTemplate);
    }

    /**
     * 根据Id删除
     * id多个用逗号分隔开
     */
    @DeleteMapping("delete/{id}")
    @ApiOperation("/根据Ids删除")
    public BasicResultVO<?> deleteByIds(@PathVariable("id") String id) {
        if (StrUtil.isNotBlank(id)) {
            List<Long> idList = Arrays.stream(id.split(StrUtil.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            messageTemplateService.removeByIds(idList);
            return BasicResultVO.success();
        }
        return BasicResultVO.fail();
    }

    /**
     * 启动模板的定时任务
     */
    @PostMapping("start/{id}")
    @ApiOperation("/启动模板的定时任务")
    public BasicResultVO<?> start(@RequestBody @PathVariable("id") Long id) {
        return messageTemplateService.startCronTask(id);
    }

    /**
     * 暂停模板的定时任务
     */
    @PostMapping("stop/{id}")
    @ApiOperation("/暂停模板的定时任务")
    public BasicResultVO<?> stop(@RequestBody @PathVariable("id") Long id) {
        messageTemplateService.stopCronTask(id);
        return BasicResultVO.success();
    }

    @PostMapping("upload")
    @ApiOperation("/上传人群文件")
    public BasicResultVO<?> upload(@RequestParam("file") MultipartFile file) {
        String filePath = path + IdUtil.fastSimpleUUID() + file.getOriginalFilename();
        try {
            File localFile = new File(filePath);
            if (!localFile.exists()) {
                boolean mkdirs = localFile.mkdirs();
                if (mkdirs) {
                    file.transferTo(localFile);
                }
            }
        } catch (Exception e) {
            log.error("MessageTemplateController#upload fail! e:{},params{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(file));
            return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR);
        }
        return BasicResultVO.successWithData(filePath);
    }
}
