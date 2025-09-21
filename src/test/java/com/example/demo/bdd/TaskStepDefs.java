package com.example.demo.bdd;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import io.cucumber.java.en.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskStepDefs {

    private TaskService taskService;
    private List<Task> tasks;
    private Task addedTask;

    @Given("the task list is empty")
    public void the_task_list_is_empty() {
        taskService = new TaskService();
        assertTrue(taskService.getTasks().isEmpty());
    }

    @When("I add a task named {string}")
    public void i_add_a_task_named(String name) {
        addedTask = taskService.addTask(name);
    }

    @Then("the task list should contain {int} task")
    public void the_task_list_should_contain_task(Integer count) {
        tasks = taskService.getTasks();
        assertEquals(count, tasks.size());
    }

    @Then("the task list should include {string}")
    public void the_task_list_should_include(String name) {
        assertTrue(taskService.getTasks().stream()
                .anyMatch(task -> task.getName().equals(name)));
    }

    @Given("I have added a task named {string}")
    public void i_have_added_a_task_named(String name) {
        if (taskService == null) {
            taskService = new TaskService();
        }
        taskService.addTask(name);
    }

    @When("I request all tasks")
    public void i_request_all_tasks() {
        tasks = taskService.getTasks();
    }

    @Then("I should see {int} tasks")
    public void i_should_see_tasks(Integer count) {
        assertEquals(count, tasks.size());
    }

    @Then("I should see {string}")
    public void i_should_see(String name) {
        assertTrue(tasks.stream().anyMatch(task -> task.getName().equals(name)));
    }
}
