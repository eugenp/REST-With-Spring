package com.baeldung.rws.endtoend.spring;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rws.domain.model.TaskStatus;
import com.baeldung.rws.web.dto.CampaignDto;
import com.baeldung.rws.web.dto.TaskDto;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CampaignsEndToEndApiIntegrationTest {

    @Autowired
    WebTestClient webClient;

    // GET - single

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseIsEqualToExpectedObject() {
        // Campaign 4 id: 20da99d1-2991-4777-b7b2-dcb14b08c468
        webClient.get()
            .uri("/campaigns/20da99d1-2991-4777-b7b2-dcb14b08c468")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CampaignDto.class)
            .isEqualTo(new CampaignDto("20da99d1-2991-4777-b7b2-dcb14b08c468", "C4", "Campaign 4", "About Campaign 4", Collections.emptySet()));
    }

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseContainsFields() {
        webClient.get()
            .uri("/campaigns/ebcbeadc-c7de-45ec-8c45-7d23a2554cc6")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CampaignDto.class)
            .value(dto -> {
                assertThat(dto.id()).isEqualTo("ebcbeadc-c7de-45ec-8c45-7d23a2554cc6");
                assertThat(dto.code()).isNotBlank();
                assertThat(dto.name()).isNotBlank();
                assertThat(dto.description()).isNotBlank();
            });
    }

    // GET - list

    @Test
    void givenPreloadedData_whenGetCampaigns_thenResponseFieldsMatch() {
        webClient.get()
            .uri("/campaigns")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(CampaignDto.class)
            .value(campaignsList -> {
                assertThat(campaignsList).hasSizeGreaterThanOrEqualTo(2);
                assertThat(campaignsList).extracting(CampaignDto::code)
                    .contains("C1", "C2", "C3");
                assertThat(campaignsList).flatExtracting(CampaignDto::tasks)
                    .extracting(TaskDto::name)
                    .contains("Task 1", "Task 2", "Task 3", "Task 4");
            });

    }

    // POST - create

    @Test
    void whenCreateNewCampaign_thenCreatedAndResponseFieldsMatch() {
        CampaignDto newCampaignBody = new CampaignDto(null, "TEST-E2E-CAMPAIGN-NEW-1", "Test - New Campaign 1", "Description of new test campaign 1", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CampaignDto.class)
            .value(resultingDto -> {
                CampaignDto expectedResult = new CampaignDto(resultingDto.id(), newCampaignBody.code(), newCampaignBody.name(), newCampaignBody.description(), emptySet());
                assertThat(resultingDto).isEqualTo(expectedResult);
            });
    }

    @Test
    void whenCreateNewCampaignWithDuplicatedCode_thenBadRequest() {
        CampaignDto newCampaignBody = new CampaignDto(null, "TEST-E2E-CAMPAIGN-NEW-2", "Test - New Campaign 3", "Description of new test campaign 3", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isCreated();

        CampaignDto newDuplicatedCodeCampaignBody = new CampaignDto(null, "TEST-E2E-CAMPAIGN-NEW-2", "Test - New Campaign 4", "Description of new test campaign 4", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newDuplicatedCodeCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    // POST - new - validations

    @Test
    void whenCreateNewCampaignWithoutRequiredCodeField_thenBadRequest() {
        // null code
        CampaignDto nullCodeCampaignBody = new CampaignDto(null, null, "Test - New Campaign Invalid", "Description of new test campaign Invalid", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(nullCodeCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    // PUT - update

    @Test
    void givenPreloadedData_whenUpdateExistingCampaign_thenOkWithSupportedFieldUpdated() {
        TaskDto taskBody = new TaskDto(null, null, "Test - Task X12", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null, null);
        Set<TaskDto> tasksListBody = Set.of(taskBody);
        CampaignDto updatedCampaignBody = new CampaignDto(null, "TEST-E2E-CAMPAIGN-UPDATED-1", "Test - Updated Campaign 2", "Description of updated test campaign 2", tasksListBody);

        // Campaign 2 id: 20da99d1-2991-4777-b7b2-dcb14b08c469
        webClient.put()
            .uri("/campaigns/20da99d1-2991-4777-b7b2-dcb14b08c469")
            .body(Mono.just(updatedCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CampaignDto.class)
            .value(dto -> {
                assertThat(dto.id()).isEqualTo("20da99d1-2991-4777-b7b2-dcb14b08c469");
                assertThat(dto.code()).isNotEqualTo(updatedCampaignBody.code());
                assertThat(dto.name()).isEqualTo(updatedCampaignBody.name());
                assertThat(dto.description()).isEqualTo(updatedCampaignBody.description());
                assertThat(dto.tasks()).isNotEmpty()
                    .noneMatch(task -> task.name()
                        .equals(taskBody.name()));
            });
    }

    @Test
    void givenPreloadedData_whenUpdateNonExistingCampaign_thenNotFound() {
        CampaignDto updatedCampaignBody = new CampaignDto(null, null, "Test - Updated Campaign 2", "Description of updated test campaign 2", null);

        webClient.put()
            .uri("/campaigns/" + UUID.randomUUID().toString())
            .body(Mono.just(updatedCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    // PUT - update - validations

    @Test
    void givenPreloadedData_whenUpdateWithInvalidFields_thenBadRequest() {
        // null name
        CampaignDto nullNameCampaignBody = new CampaignDto(null, "TEST-E2E-CAMPAIGN-UPDATED-3", null, "Description of updated test campaign 3", null);

        webClient.put()
            .uri("/campaigns/2")
            .body(Mono.just(nullNameCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

}
