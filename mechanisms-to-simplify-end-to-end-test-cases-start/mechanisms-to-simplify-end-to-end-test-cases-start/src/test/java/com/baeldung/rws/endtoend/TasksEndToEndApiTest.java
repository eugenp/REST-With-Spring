package com.baeldung.rws.endtoend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rws.web.dto.TaskDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TasksEndToEndApiTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void givenPreloadedData_whenGetSingleTask_thenResponseFieldsMatch() {
        webClient.get()
            .uri("/tasks/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(TaskDto.class)
            .value(dto -> {
                assertThat(dto.id()).isEqualTo(1L);
                assertThat(dto.description()).isNotBlank();
                assertThat(dto.campaignId()).isNotNull();
                assertThat(dto.dueDate()).hasYear(2030);
            });
    }

    @Test
    void givenPreloadedData_whenGetTasks_thenResponseFieldsMatch() {
        webClient.get()
            .uri("/tasks")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(TaskDto.class)
            .value(tasksList -> {
                assertThat(tasksList).hasSizeGreaterThanOrEqualTo(2);
                assertThat(tasksList).extracting(TaskDto::campaignId)
                    .allMatch(campaignId -> campaignId > 0);
                assertThat(tasksList).extracting(TaskDto::name)
                    .allSatisfy(name -> assertThat(name).contains("Task"));
            });
    }
}
