package com.warrior.luminate;


import com.warrior.luminate.domain.SendRequest;
import com.warrior.luminate.service.SendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LuminateWebApplicationTests {

    @Autowired
    private SendService sendService;

    @Test
    void contextLoads() {
    }

    @Test
    void testSend() {
        SendRequest sendRequest = new SendRequest();
        sendService.send(sendRequest);
    }

}
