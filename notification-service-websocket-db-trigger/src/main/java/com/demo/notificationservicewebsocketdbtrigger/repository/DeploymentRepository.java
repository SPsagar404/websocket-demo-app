package com.demo.notificationservicewebsocketdbtrigger.repository;

import com.demo.notificationservicewebsocketdbtrigger.entity.Deployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeploymentRepository extends JpaRepository<Deployment, Long> {
}
