package com.marmik.backend.controllers;

import com.marmik.backend.Task;
import com.marmik.backend.services.TaskService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws Exception {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable ObjectId id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable ObjectId id, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(id, updatedTask);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable ObjectId id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProjectId(@PathVariable ObjectId projectId) {
        return ResponseEntity.ok(taskService.getTasksByProjectId(projectId));
    }

    // Assign a user to a task
    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<Task> assignUserToTask(@PathVariable ObjectId taskId, @PathVariable ObjectId userId) {
        try {
            Task updatedTask = taskService.assignUserToTask(taskId, userId);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Remove a user from a task
    @PutMapping("/{taskId}/remove-user")
    public ResponseEntity<Task> removeUserFromTask(@PathVariable ObjectId taskId) {
        try {
            Task updatedTask = taskService.removeUserFromTask(taskId);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
