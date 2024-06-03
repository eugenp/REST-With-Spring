package com.baeldung.rwsb.endtoend;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rwsb.domain.model.TaskStatus;
import com.baeldung.rwsb.web.dto.TaskDto;

import reactor.core.publisher.Mono;

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

    @Test
    void whenCreateNewTaskWithoutRequiredCampaignObject_thenBadRequest() {
        // null campaign
        TaskDto nullCampaignObjectTaskBody = new TaskDto(null, null, "Test - Task 1", "Description of task 1", LocalDate.of(2030, 01, 01), TaskStatus.TO_DO, null, null);

        webClient.post()
            .uri("/tasks")
            .bodyValue(nullCampaignObjectTaskBody)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    void whenDeleteExistingTask_thenMethodNotAllowedError() {
        webClient.delete()
            .uri("/tasks/1")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatusCode.valueOf(405));
    }
}
