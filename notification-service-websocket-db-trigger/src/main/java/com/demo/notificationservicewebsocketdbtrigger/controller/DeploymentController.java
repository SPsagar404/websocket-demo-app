package com.demo.notificationservicewebsocketdbtrigger.controller;

import com.demo.notificationservicewebsocketdbtrigger.entity.Deployment;
import com.demo.notificationservicewebsocketdbtrigger.service.DeploymentService;
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
        return ResponseEntity.ok(deploymentService.getAllDeployments());
    }

    // GET deployment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Deployment> getDeploymentById(@PathVariable Long id) {
        return deploymentService.getDeploymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create deployment
    @PostMapping
    public ResponseEntity<Deployment> createDeployment(@RequestBody Deployment deployment) {
        return ResponseEntity.ok(deploymentService.createDeployment(deployment));
    }

    // PUT update deployment
    @PutMapping("/{id}")
    public ResponseEntity<Deployment> updateDeployment(@PathVariable Long id, @RequestBody Deployment updatedDeployment) {
        try {
            return ResponseEntity.ok(deploymentService.updateDeployment(id, updatedDeployment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE deployment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeployment(@PathVariable Long id) {
        deploymentService.deleteDeployment(id);
        return ResponseEntity.noContent().build();
    }
}
