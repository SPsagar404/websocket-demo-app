package com.demo.notificationsystem_websocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationSystemWebsocketClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationSystemWebsocketClientApplication.class, args);
    }

}
