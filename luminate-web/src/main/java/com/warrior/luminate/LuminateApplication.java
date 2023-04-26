package com.warrior.luminate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author warrior
 */
@SpringBootApplication
public class LuminateApplication {

    public static void main(String[] args) {
        try {
            System.setProperty("apollo.config-service", "http://192.168.20.160:8080");
            SpringApplication.run(LuminateApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
