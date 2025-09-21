package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Task {
    private int id;

    @NotBlank(message = "Task name must not be blank")
    private String name;

    public Task() {}

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
