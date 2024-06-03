package com.baeldung.rwsb.contract.spring;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;

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

import com.baeldung.rwsb.domain.model.TaskStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractTasksApiIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Value("classpath:task.json")
    Resource task;

    @Value("classpath:task-with-assignee.json")
    Resource taskWithAssignee;

    // GET - single

    @Test
    void givenPreloadedData_whenGetSingleTask_thenResponseContainsFields() { // @formatter:off
        webClient.get()
            .uri("/tasks/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .value(equalTo(1L), Long.class)
            .jsonPath("$.name")
            .exists()
            .jsonPath("$.status")
            .isEqualTo(TaskStatus.TO_DO.toValue())
            .jsonPath("$.description")
            .value(containsString("Description"));
    } // @formatter:on

    // POST - create

    @Test
    void whenCreateNewTaskWithValidTaskJsonInput_thenSuccess() throws Exception {
        String taskJson = generateTaskInput(task);

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
            .isEqualTo("Test - Template Task Name")
            .jsonPath("$.campaignId")
            .value(not(blankOrNullString()))
            .jsonPath("$.dueDate")
            .isEqualTo("2050-12-31");
    }

    private String generateTaskInput(Resource resource) throws Exception {
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }
}