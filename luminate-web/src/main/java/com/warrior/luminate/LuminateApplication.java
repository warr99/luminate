package com.warrior.luminate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author 3y
 */
@SpringBootApplication
public class LuminateApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(LuminateApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
