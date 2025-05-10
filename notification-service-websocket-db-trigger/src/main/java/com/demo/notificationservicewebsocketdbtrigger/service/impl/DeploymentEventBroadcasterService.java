package com.demo.notificationservicewebsocketdbtrigger.service.impl;

import com.demo.notificationservicewebsocketdbtrigger.entity.DeploymentEvent;
import com.demo.notificationservicewebsocketdbtrigger.repository.DeploymentEventRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeploymentEventBroadcasterService {

    private final SimpMessagingTemplate messagingTemplate;
    private final DeploymentEventRepository eventRepository;

    public DeploymentEventBroadcasterService(SimpMessagingTemplate messagingTemplate,
                                      DeploymentEventRepository eventRepository) {
        this.messagingTemplate = messagingTemplate;
        this.eventRepository = eventRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void broadcastNewEvents() {
        System.out.println("inside broadcastNewEvents() method... broadcasting new events to WebSocket clients! 5 seconds have passed. Current time: " + System.currentTimeMillis() / 1000 + " seconds.");
        List<DeploymentEvent> events = eventRepository.findTop1ByOrderByCreatedAtDesc();
        for (DeploymentEvent event : events) {
            System.out.println("message sent to WebSocket clients: " + event.getMessage() + " at " + event.getCreatedAt() + " seconds. Current time: " + System.currentTimeMillis() / 100);
            messagingTemplate.convertAndSend("/topic/notification", event.getMessage());
            System.out.println("message sent successfully! Current time: " + System.currentTimeMillis() / 1000 + " seconds. 5 seconds have passed.");
        }
    }

}
