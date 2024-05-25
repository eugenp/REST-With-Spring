package com.baeldung.rws.contract.spring;

import static org.hamcrest.CoreMatchers.equalTo;

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
public class ContractWorkerApiIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Value("classpath:new-worker.json")
    Resource newWorkerResource;

    @Value("classpath:old-worker.json")
    Resource oldWorkerResource;

    // GET - single

    @Test
    void givenPreloadedData_whenGetSingleWorkerUsingAcceptHeader_thenResponseContainsFieldsWithUserStructure() { // @formatter:off
        webClient.get()
            .uri("/workers/1")
            .accept(MediaType.parseMediaType("application/vnd.baeldung.new-worker+json"))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .value(equalTo(1L), Long.class)
            .jsonPath("$.firstName")
            .exists()
            .jsonPath("$.user")
            .exists()
            .jsonPath("$.user.email")
            .isEqualTo("john@test.com");
    } // @formatter:on

    @Test
    void givenPreloadedData_whenGetSingleWorkerWithoutHeader_thenResponseContainsFieldsWithOldtructure() { // @formatter:off
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
            .jsonPath("$.user")
            .doesNotExist()
            .jsonPath("$.email")
            .isEqualTo("john@test.com");
    } // @formatter:on

    @Test
    void givenPreloadedData_whenCreateWorkerUsingAcceptHeaderAndNewStructure_thenCreated() throws Exception { // @formatter:off
        String newWorkerBody = generateWorkerJsonFromFile(newWorkerResource);
        
        webClient.post()
            .uri("/workers")
            .accept(MediaType.parseMediaType("application/vnd.baeldung.new-worker+json"))
            .contentType(MediaType.parseMediaType("application/vnd.baeldung.new-worker+json"))
            .bodyValue(newWorkerBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .exists()
            .jsonPath("$.firstName")
            .exists()
            .jsonPath("$.user")
            .exists()
            .jsonPath("$.user.email")
            .isEqualTo("john@new-worker.com");
    } // @formatter:on

    @Test
    void givenPreloadedData_whenCreateWorkerUsingAcceptHeaderButOldStructure_thenBadRequest() throws Exception { // @formatter:off
        String oldWorkerBody = generateWorkerJsonFromFile(oldWorkerResource);
        
        webClient.post()
            .uri("/workers")
            .accept(MediaType.parseMediaType("application/vnd.baeldung.new-worker+json"))
            .contentType(MediaType.parseMediaType("application/vnd.baeldung.new-worker+json"))
            .bodyValue(oldWorkerBody)
            .exchange()
            .expectStatus()
            .isBadRequest();
    } // @formatter:on

    @Test
    void givenPreloadedData_whenCreateWorkerWithoutHeaderAndOldStructure_thenResponseContainsFieldsWithOldtructure() throws Exception { // @formatter:off
        String oldWorkerBody = generateWorkerJsonFromFile(oldWorkerResource);
        
        webClient.post()
            .uri("/workers")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(oldWorkerBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .exists()
            .jsonPath("$.firstName")
            .exists()
            .jsonPath("$.user")
            .doesNotExist()
            .jsonPath("$.email")
            .isEqualTo("john@old-worker.com");
    } // @formatter:on

    @Test
    void givenPreloadedData_whenGetSingleWorkerWithoutHeaderAndNewStructure_thenBadRequest() throws Exception { // @formatter:off
        String newWorkerBody = generateWorkerJsonFromFile(newWorkerResource);
        
        webClient.post()
            .uri("/workers")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newWorkerBody)
            .exchange()
            .expectStatus()
            .isBadRequest();
    } // @formatter:on

    private static String generateWorkerJsonFromFile(Resource resource) throws Exception {
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }
}