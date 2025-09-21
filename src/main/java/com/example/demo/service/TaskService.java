package com.example.demo.service;

import com.example.demo.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Service
public class TaskService {
    
    private final Map<Integer, Task> taskStore = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);
    
    private static final int MAX_TASK_NAME_LENGTH = 255;
    private static final int MIN_VALID_ID = 1;
    private static final Pattern DANGEROUS_PATTERN = Pattern.compile("[<>\"'&;]");
    private static final Pattern HTML_TAG_PATTERN = Pattern.compile("<[^>]*>");

    public Task addTask(String name) {
        String sanitizedName = validateAndSanitizeTaskName(name);
        Task task = new Task(counter.getAndIncrement(), sanitizedName);
        taskStore.put(task.getId(), task);
        return task;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(taskStore.values());
    }

    public Task getTaskById(int id) {
        validateId(id);
        return taskStore.get(id);
    }

    public Task updateTask(int id, String newName) {
        validateId(id);
        String sanitizedName = validateAndSanitizeTaskName(newName);
        
        Task task = taskStore.get(id);
        if (task != null) {
            task.setName(sanitizedName);
        }
        return task;
    }

    public boolean deleteTask(int id) {
        validateId(id);
        return taskStore.remove(id) != null;
    }
    
    private String validateAndSanitizeTaskName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Task name cannot be null");
        }
        
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }
        
        if (trimmed.length() > MAX_TASK_NAME_LENGTH) {
            throw new IllegalArgumentException("Task name too long (max " + MAX_TASK_NAME_LENGTH + " chars)");
        }
        
        // Remove HTML tags and dangerous characters
        String sanitized = HTML_TAG_PATTERN.matcher(trimmed).replaceAll("");
        sanitized = DANGEROUS_PATTERN.matcher(sanitized).replaceAll("_");
        
        if (sanitized.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name contains only invalid characters");
        }
        
        return sanitized;
    }
    
    private void validateId(int id) {
        if (id < MIN_VALID_ID) {
            throw new IllegalArgumentException("ID must be at least " + MIN_VALID_ID);
        }
    }
    
    public int getTaskCount() {
        return taskStore.size();
    }
    
    public boolean taskExists(int id) {
        try {
            validateId(id);
            return taskStore.containsKey(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
