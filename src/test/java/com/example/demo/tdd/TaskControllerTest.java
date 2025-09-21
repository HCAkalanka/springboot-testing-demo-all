package com.example.demo.tdd;

import com.example.demo.controller.TaskController;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private TaskService taskService;
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        taskController = new TaskController(taskService);
    }

    @Test
    void testGetTasks() {
        when(taskService.getTasks()).thenReturn(
                Arrays.asList(new Task(1, "Task 1"), new Task(2, "Task 2"))
        );

        ResponseEntity<List<Task>> response = taskController.getTasks();
        List<Task> tasks = response.getBody();

        assertEquals(2, tasks.size());
        assertEquals(200, response.getStatusCode().value());
        verify(taskService, times(1)).getTasks();
    }

    @Test
    void testAddTask() {
        Task mockTask = new Task(1, "Write Code");
        when(taskService.addTask("Write Code")).thenReturn(mockTask);

        ResponseEntity<Task> response = taskController.addTask("Write Code");
        Task returnedTask = response.getBody();

        assertEquals("Write Code", returnedTask.getName());
        assertEquals(201, response.getStatusCode().value());
        verify(taskService, times(1)).addTask("Write Code");
    }
}
