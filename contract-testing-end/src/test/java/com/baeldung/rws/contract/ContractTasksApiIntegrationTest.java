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

    @Value("classpath:task-v1.json")
    Resource resource;

    @Test
    void createNewTask_withValidTaskJsonString_thenSuccess() throws Exception {
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
    }

    @Test
    void createNewTask_withValidTaskJsonFromFile_thenSuccess() throws Exception {
        String taskJson = generateStaticTaskJsonFromFile();

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
    }

    private static String generateTaskJson() {
        return """
             {
                 "name": "Test - New Task 1",
                 "description": "Description of New Task 1",
                 "dueDate": "2050-12-30",
                 "campaignId": 1
             }
            """;
    }

    private String generateStaticTaskJsonFromFile() throws Exception {
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }
}
