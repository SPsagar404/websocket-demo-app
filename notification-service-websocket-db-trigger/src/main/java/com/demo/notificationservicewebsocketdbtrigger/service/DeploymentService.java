package com.demo.notificationservicewebsocketdbtrigger.service;

import com.demo.notificationservicewebsocketdbtrigger.entity.Deployment;

import java.util.List;
import java.util.Optional;

public interface DeploymentService {
    List<Deployment> getAllDeployments();
    Optional<Deployment> getDeploymentById(Long id);
    Deployment createDeployment(Deployment deployment);
    Deployment updateDeployment(Long id, Deployment updatedDeployment);
    void deleteDeployment(Long id);
}
