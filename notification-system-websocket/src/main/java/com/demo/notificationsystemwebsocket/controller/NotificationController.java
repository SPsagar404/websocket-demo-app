package com.demo.notificationsystemwebsocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public String showNotificationPage(String message) {
        System.out.println("Notification page");
        System.out.println(message);
        return message;
    }
}
