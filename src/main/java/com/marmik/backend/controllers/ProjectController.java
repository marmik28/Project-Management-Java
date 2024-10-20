package com.marmik.backend.controllers;

import com.marmik.backend.Project;
import com.marmik.backend.services.ProjectService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) throws Exception {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable ObjectId id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable ObjectId id, @RequestBody Project updatedProject) {
        Project project = projectService.updateProject(id, updatedProject);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable ObjectId id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // Add a user to a project
    @PutMapping("/{projectId}/add-user/{userId}")
    public ResponseEntity<Project> addUserToProject(@PathVariable ObjectId projectId, @PathVariable ObjectId userId) {
        try {
            Project updatedProject = projectService.addUserToProject(projectId, userId);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Remove a user from a project
    @PutMapping("/{projectId}/remove-user/{userId}")
    public ResponseEntity<Project> removeUserFromProject(@PathVariable ObjectId projectId, @PathVariable ObjectId userId) {
        try {
            Project updatedProject = projectService.removeUserFromProject(projectId, userId);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
