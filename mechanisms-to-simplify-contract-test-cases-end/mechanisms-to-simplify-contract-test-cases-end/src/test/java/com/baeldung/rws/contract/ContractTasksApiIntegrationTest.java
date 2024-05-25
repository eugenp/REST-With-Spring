package com.baeldung.rws.contract;

import static org.hamcrest.Matchers.greaterThan;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.FileCopyUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractTasksApiIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Value("classpath:task.json")
    Resource resource;

    @Test
    void whenCreateNewTask_thenSuccess() throws Exception { // @formatter:off
        String taskJson = generateTaskJson();

        webClient.post()
            .uri("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(taskJson)
            .exchange()
            .expectStatus()
            .isCreated();
    } // @formatter:on

    @Test
    void whenCreateNewTask_thenSuccessWithExpectedFields() throws Exception { // @formatter:off
        String taskJson = generateTaskJson();

        webClient.post()
            .uri("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(taskJson)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .exists()
            .jsonPath("$.name")
            .isEqualTo("Test - New Task 1")
            .jsonPath("$.campaignId")
            .value(greaterThan(0))
            .jsonPath("$.dueDate")
            .isEqualTo("2050-12-30");
    } // @formatter:on

    @Test
    void whenCreateNewTaskFromString_thenSuccessWithExpectedFields() throws Exception { // @formatter:off
        String taskJson = generateTaskJsonFromString();

        webClient.post()
            .uri("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(taskJson)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .exists()
            .jsonPath("$.name")
            .isEqualTo("Test - New Task 2")
            .jsonPath("$.campaignId")
            .value(greaterThan(0))
            .jsonPath("$.dueDate")
            .isEqualTo("2050-12-30");
    } // @formatter:on

    private String generateTaskJson() throws Exception {
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }

    private static String generateTaskJsonFromString() {
        return """
             {
                 "name": "Test - New Task 2",
                 "description": "Description of New Task 2",
                 "dueDate": "2050-12-30",
                 "campaignId": 1
             }
            """;
    }
}
