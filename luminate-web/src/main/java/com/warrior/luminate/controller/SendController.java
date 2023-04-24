package com.warrior.luminate.controller;


import com.warrior.luminate.domain.BatchSendRequest;
import com.warrior.luminate.domain.SendRequest;
import com.warrior.luminate.domain.SendResponse;
import com.warrior.luminate.service.SendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author WARRIOR
 * @version 1.0
 * 后台管理界面发送消息接口
 */
@Api(value = "SendController", tags = {"消息接口"})
@RestController
public class SendController {
    private final SendService sendService;

    @Autowired
    public SendController(SendService sendService) {
        this.sendService = sendService;
    }

    @ApiOperation(value = "发送消息")
    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest) {
        return sendService.send(sendRequest);
    }

    @ApiOperation(value = "批量发送消息")
    @PostMapping("/batchSend")
    public SendResponse batchSend(@RequestBody BatchSendRequest batchSendRequest) {
        return sendService.batchSend(batchSendRequest);
    }

}
