package com.demo.notificationservicewebsocketdbtrigger.repository;

import com.demo.notificationservicewebsocketdbtrigger.entity.DeploymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeploymentEventRepository extends JpaRepository<DeploymentEvent, Long> {

    List<DeploymentEvent> findTop1ByOrderByCreatedAtDesc();
}

