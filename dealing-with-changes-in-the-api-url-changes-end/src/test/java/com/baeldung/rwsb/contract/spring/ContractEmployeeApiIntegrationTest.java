package com.baeldung.rwsb.contract.spring;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractEmployeeApiIntegrationTest {

    @Autowired
    WebTestClient webClient;
    // GET - single

    @Test
    void givenPreloadedData_whenGetSingleWorker_thenResponseContainsFields() {
        webClient.get()
            .uri("/workers/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .value(equalTo(1L), Long.class)
            .jsonPath("$.firstName")
            .exists()
            .jsonPath("$.email")
            .isEqualTo("john@test.com");
    }

    @Test
    void givenPreloadedData_whenGetSingleEmployee_thenResponseContainsFields() {
        webClient.get()
            .uri("/employees/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .value(equalTo(1L), Long.class)
            .jsonPath("$.firstName")
            .exists()
            .jsonPath("$.email")
            .isEqualTo("john@test.com");
    }
}
