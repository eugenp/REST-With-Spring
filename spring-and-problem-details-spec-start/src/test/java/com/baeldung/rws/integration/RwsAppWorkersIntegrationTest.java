package com.baeldung.rws.integration;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rws.web.dto.WorkerDto;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RwsAppWorkersIntegrationTest {

    @Autowired
    WebTestClient webClient;

    // GET - by id

    @Test
    void givenPreloadedData_whenGetWorker_thenOk() {
        webClient.get()
            .uri("/workers/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(1L)
            .jsonPath("$.email")
            .isNotEmpty()
            .jsonPath("$.firstName")
            .isNotEmpty()
            .jsonPath("$.lastName")
            .isNotEmpty();
    }

    // POST - new

    @Test
    void whenCreateNewWorker_thenCreated() {
        WorkerDto newWorkerBody = new WorkerDto(null, "test@testemail.com", "Test First Name", "Test Last Name");

        webClient.post()
            .uri("/workers")
            .body(Mono.just(newWorkerBody), WorkerDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(1))
            .jsonPath("$.email")
            .isEqualTo("test@testemail.com")
            .jsonPath("$.firstName")
            .isEqualTo("Test First Name")
            .jsonPath("$.lastName")
            .isEqualTo("Test Last Name");
    }

    @Test
    void whenCreateNewWorkerPresentingExistingId_thenCreatedWithoutModifyingExistingWorker() {
        WorkerDto newWorkerBody = new WorkerDto(1L, "test2@testemail.com", "Test First Name 2", "Test Last Name 2");

        webClient.post()
            .uri("/workers")
            .body(Mono.just(newWorkerBody), WorkerDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(1))
            .jsonPath("$.email")
            .isEqualTo("test2@testemail.com")
            .jsonPath("$.firstName")
            .isEqualTo("Test First Name 2")
            .jsonPath("$.lastName")
            .isEqualTo("Test Last Name 2");
    }

    @Test
    void whenCreateNewWorkerUsingExistingEmail_thenClientError() {
        WorkerDto newWorkerBody = new WorkerDto(null, "test3@testemail.com", "Test First Name 3", "Test Last Name 3");

        webClient.post()
            .uri("/workers")
            .body(Mono.just(newWorkerBody), WorkerDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(1));

        WorkerDto newDuplicatedEmailWorkerBody = new WorkerDto(null, "test3@testemail.com", "Test First Name 4", "Test Last Name 4");

        webClient.post()
            .uri("/workers")
            .body(Mono.just(newDuplicatedEmailWorkerBody), WorkerDto.class)
            .exchange()
            .expectStatus()
            .is4xxClientError()
            .expectBody()
            .jsonPath("$.error")
            .isNotEmpty();
    }

    // PUT - update

    @Test
    void givenPreloadedData_whenUpdateExistingWorker_thenOkWithSupportedFieldUpdated() {
        WorkerDto updatedWorkerBody = new WorkerDto(null, "updated@email.com", "Updated First Name", "Updated Last Name");

        webClient.put()
            .uri("/workers/1")
            .body(Mono.just(updatedWorkerBody), WorkerDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(1L)
            .jsonPath("$.email")
            .value(not("updated@email.com"))
            .jsonPath("$.firstName")
            .isEqualTo("Updated First Name")
            .jsonPath("$.lastName")
            .isEqualTo("Updated Last Name");
    }
}