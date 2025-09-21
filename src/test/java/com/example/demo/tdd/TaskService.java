package com.example.demo.tdd;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void testAddTask() {
        Task task = taskService.addTask("Read Book");

        assertNotNull(task);
        assertEquals("Read Book", task.getName());

        List<Task> tasks = taskService.getTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void testGetTaskById() {
        Task task = taskService.addTask("Learn Spring Boot");
        Task found = taskService.getTaskById(task.getId());

        assertNotNull(found);
        assertEquals("Learn Spring Boot", found.getName());
    }

    @Test
    void testUpdateTask() {
        Task task = taskService.addTask("Old Name");
        Task updated = taskService.updateTask(task.getId(), "New Name");

        assertNotNull(updated);
        assertEquals("New Name", updated.getName());
    }

    @Test
    void testDeleteTask() {
        Task task = taskService.addTask("Do Homework");
        boolean deleted = taskService.deleteTask(task.getId());

        assertTrue(deleted);
        assertTrue(taskService.getTasks().isEmpty());
    }
}
