package com.baeldung.rwsb.endtoend;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rwsb.web.dto.TaskDto;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TasksEndToEndApiTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void createNewTask_withValidTaskObject_thenSuccess() {

        TaskDto newTaskBody = new TaskDto(null, null, "Test - New Task 1", "Description of New Task 1", LocalDate.of(2050, 12, 30), null, 1L, null);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated();
    }

    @Test
    void createNewTask_withValidTaskObject_thenSuccessWithCorrectContentFields() {

        TaskDto newTaskBody = new TaskDto(null, null, "Test - New Task 1", "Description of New Task 1", LocalDate.of(2050, 12, 30), null, 1L, null);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(TaskDto.class)
            .value(dto -> {
                assertThat(dto.id()).isNotNull();
                assertThat(dto.name()).isEqualTo("Test - New Task 1");
                assertThat(dto.campaignId()).isNotNull();
                assertThat(dto.dueDate()).hasYear(2050);
            });
    }

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
