package com.demo.notificationservicewebsocketdbtrigger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "deployment_events")
public class DeploymentEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deployment_id")
    private Long deploymentId;

    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

