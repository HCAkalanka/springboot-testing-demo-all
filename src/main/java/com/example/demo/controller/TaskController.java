package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Task REST Controller with Quality Improvements
 * 
 * Provides REST API endpoints for task management with:
 * - Comprehensive error handling
 * - Input validation
 * - Standardized response patterns
 * - SonarQube compliance fixes
 * 
 * @author Spring Boot Testing Demo
 * @version 1.1 - Quality Improved
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    /**
     * Constructor with dependency injection
     * 
     * @param service The task service to use
     */
    public TaskController(TaskService service) {
        this.service = service;
    }

    /**
     * Creates a new task
     * 
     * @param task The task name
     * @return The created task
     */
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestParam @NotBlank String task) {
        try {
            // Additional validation at controller level
            if (task.trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task must not be blank");
            }
            
            Task createdTask = service.addTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
            
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "An error occurred while creating the task");
        }
    }

    /**
     * Retrieves all tasks
     * 
     * @return List of all tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        try {
            List<Task> tasks = service.getTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "An error occurred while retrieving tasks");
        }
    }

    /**
     * Retrieves a specific task by ID
     * 
     * @param id The task ID
     * @return The task if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id) {
        try {
            Task task = service.getTaskById(id);
            // SonarQube Fix: Extract common validation to reduce duplication
            validateTaskExists(task, id);
            return ResponseEntity.ok(task);
            
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "An error occurred while retrieving the task");
        }
    }

    /**
     * Updates an existing task
     * 
     * @param id The task ID to update
     * @param task The new task name
     * @return The updated task
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, 
                                         @RequestParam @NotBlank String task) {
        try {
            Task updated = service.updateTask(id, task);
            // SonarQube Fix: Use extracted validation method
            validateTaskExists(updated, id);
            return ResponseEntity.ok(updated);
            
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "An error occurred while updating the task");
        }
    }

    /**
     * Deletes a task by ID
     * 
     * @param id The task ID to delete
     * @return Success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        try {
            boolean deleted = service.deleteTask(id);
            if (!deleted) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    String.format("Task with ID %d not found", id));
            }
            return ResponseEntity.ok("Task deleted successfully");
            
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ResponseStatusException e) {
            throw e; // Re-throw ResponseStatusException as-is
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "An error occurred while deleting the task");
        }
    }
    
    /**
     * Gets task count (utility endpoint)
     * 
     * @return Current number of tasks
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getTaskCount() {
        try {
            int count = service.getTaskCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "An error occurred while counting tasks");
        }
    }
    
    /**
     * SonarQube Fix: Extract common validation logic to reduce code duplication
     * Validates that a task exists and throws appropriate exception if not
     * 
     * @param task The task to validate
     * @param id The task ID for error messaging
     * @throws ResponseStatusException if task is null
     */
    private void validateTaskExists(Task task, int id) {
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                String.format("Task with ID %d not found", id));
        }
    }
}
