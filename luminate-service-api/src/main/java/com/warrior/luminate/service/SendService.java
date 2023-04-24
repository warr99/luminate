package com.warrior.luminate.service;


import com.warrior.luminate.domain.BatchSendRequest;
import com.warrior.luminate.domain.SendRequest;
import com.warrior.luminate.domain.SendResponse;

/**
 * @author WARRIOR
 * @version 1.0
 * 发送消息接口
 */
public interface SendService {
    /**
     * 单文案发送接口
     *
     * @param sendRequest 发送请求的参数
     * @return SendResponse
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 批量发接口
     *
     * @param batchSendRequest 批量发送请求的参数
     * @return SendResponse
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);
}
