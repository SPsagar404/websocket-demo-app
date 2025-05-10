package com.demo.notificationservicewebsocketdbtrigger.service.impl;

import com.demo.notificationservicewebsocketdbtrigger.entity.Deployment;
import com.demo.notificationservicewebsocketdbtrigger.repository.DeploymentRepository;
import com.demo.notificationservicewebsocketdbtrigger.service.DeploymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeploymentServiceImpl implements DeploymentService {

    private final DeploymentRepository deploymentRepository;

    public DeploymentServiceImpl(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    @Override
    public List<Deployment> getAllDeployments() {
        return deploymentRepository.findAll();
    }

    @Override
    public Optional<Deployment> getDeploymentById(Long id) {
        return deploymentRepository.findById(id);
    }

    @Override
    public Deployment createDeployment(Deployment deployment) {
        return deploymentRepository.save(deployment);
    }

    @Override
    public Deployment updateDeployment(Long id, Deployment updatedDeployment) {
        return deploymentRepository.findById(id).map(existing -> {
            existing.setModelName(updatedDeployment.getModelName());
            existing.setStatus(updatedDeployment.getStatus());
            return deploymentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Deployment not found with id: " + id));
    }

    @Override
    public void deleteDeployment(Long id) {
        deploymentRepository.deleteById(id);
    }
}
