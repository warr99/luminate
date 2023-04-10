package com.warrior.luminate.controller;


import com.warrior.luminate.domain.SendRequest;
import com.warrior.luminate.domain.SendResponse;
import com.warrior.luminate.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author WARRIOR
 * @version 1.0
 * 后台管理界面发送消息接口
 */
@RestController
public class SendController {
    private final SendService sendService;

    @Autowired
    public SendController(SendService sendService) {
        this.sendService = sendService;
    }

    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest) {
        System.out.println(sendRequest.toString());
        return sendService.send(sendRequest);
    }

}
