package com.warrior.luminate;

import com.warrior.luminate.service.CronTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author WARRIOR
 * @version 1.0
 */
@SpringBootTest
public class CronTaskServiceTest {
    @Autowired
    private CronTaskService cronTaskService;
    @Test
    void testSaveCronTask() {
        cronTaskService.saveCronTask(null);
    }
}
