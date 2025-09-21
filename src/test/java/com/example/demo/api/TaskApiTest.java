package com.example.demo.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskApiTest {
    @LocalServerPort
    int port;

    @Test
    void testAddAndGetTasks() {
        RestAssured.given().port(port)
                .param("task", "API Task")
                .when().post("/tasks")
                .then().statusCode(201)
                .body("name", equalTo("API Task"));

    RestAssured.given().port(port)
        .when().get("/tasks")
        .then().statusCode(200)
        .body("name", hasItem("API Task"));
    }

    @Test
    void testAddTaskWithoutParamShouldFail() {
    RestAssured.given().port(port)
        .when().post("/tasks")
        .then().statusCode(400);
    }
}
