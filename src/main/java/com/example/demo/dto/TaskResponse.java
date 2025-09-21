package com.example.demo.dto;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean done;

    public TaskResponse() {}
    public TaskResponse(Long id, String title, String description, boolean done) {
        this.id = id; this.title = title; this.description = description; this.done = done;
    }
    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
}
