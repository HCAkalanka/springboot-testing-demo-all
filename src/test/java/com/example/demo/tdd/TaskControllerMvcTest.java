package com.example.demo.tdd;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class TaskControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void testGetTasks() throws Exception {
        when(taskService.getTasks()).thenReturn(
                Arrays.asList(new Task(1, "Task 1"), new Task(2, "Task 2"))
        );

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Task 1"));
    }

    @Test
    void testAddTask() throws Exception {
        Task task = new Task(1, "New Task");
        when(taskService.addTask("New Task")).thenReturn(task);

        mockMvc.perform(post("/tasks?task=New Task"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Task"));
    }
}
