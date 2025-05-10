package com.demo.notificationservicewebsocketdbtrigger.controller;

import com.demo.notificationservicewebsocketdbtrigger.entity.Deployment;
import com.demo.notificationservicewebsocketdbtrigger.service.DeploymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deployments")
public class DeploymentController {

    private final DeploymentService deploymentService;

    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    // GET all deployments
    @GetMapping
    public ResponseEntity<List<Deployment>> getAllDeployments() {
        List<Deployment> deployments = deploymentService.getAllDeployments();
        return deployments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(deployments);
    }

    // GET deployment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Deployment> getDeploymentById(@PathVariable Long id) {
        return deploymentService.getDeploymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST create deployment
    @PostMapping
    public ResponseEntity<Deployment> createDeployment(@RequestBody Deployment deployment) {
        Deployment createdDeployment = deploymentService.createDeployment(deployment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeployment);
    }

    // PUT update deployment
    @PutMapping("/{id}")
    public ResponseEntity<Deployment> updateDeployment(@PathVariable Long id, @RequestBody Deployment updatedDeployment) {
        try {
            Deployment deployment = deploymentService.updateDeployment(id, updatedDeployment);
            return ResponseEntity.ok(deployment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE deployment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeployment(@PathVariable Long id) {
        if (deploymentService.deleteDeployment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
