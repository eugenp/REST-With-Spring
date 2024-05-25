package com.baeldung.rws.endtoend;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rws.domain.model.TaskStatus;
import com.baeldung.rws.web.dto.CampaignDto;
import com.baeldung.rws.web.dto.TaskDto;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CampaignsEndToEndApiTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void givenRunningService_whenGetSingleCampaign_thenExpectStatus() {
        webClient.get()
            .uri("/campaigns/3")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseBodyContainsExpectedValues() {
        webClient.get()
            .uri("/campaigns/3")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CampaignDto.class)
            .isEqualTo(new CampaignDto(3L, "C3", "Campaign 3", "About Campaign 3", Collections.emptySet()));
    }

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseFieldsMatch() {
        webClient.get()
            .uri("/campaigns/3")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CampaignDto.class)
            .value(dto -> {
                assertThat(dto.id()).isEqualTo(3L);
                assertThat(dto.description()).isNotBlank();
                assertThat(dto.name()).contains("Campaign");
                assertThat(dto.code()).doesNotContainAnyWhitespaces();
            });
    }

    @Test
    void givenPreloadedData_whenGetCampaigns_thenResponseFieldsMatch() {
        webClient.get()
            .uri("/campaigns")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(CampaignDto.class)
            .value(campaignsList -> {
                assertThat(campaignsList).hasSizeGreaterThanOrEqualTo(3);
                assertThat(campaignsList).extracting(CampaignDto::code)
                    .contains("C1", "C2", "C3");
                assertThat(campaignsList).flatExtracting(CampaignDto::tasks)
                    .extracting(TaskDto::name)
                    .contains("Task 1", "Task 2", "Task 3", "Task 4");
            });
    }

    @Test
    void givenPreloadedData_whenGetNonExistingCampaign_thenNotFoundErrorWithUnknownStructure() {
        ParameterizedTypeReference<Map<String, Object>> mapType = new ParameterizedTypeReference<Map<String, Object>>() {
        };
        webClient.get()
            .uri("/campaigns/99")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody(mapType)
            .value(mapResponseBody -> {
                assertThat(mapResponseBody).containsEntry("status", 404);
                assertThat(mapResponseBody).containsEntry("title", "Not Found");
            });
    }

    @Test
    void givenPreloadedData_whenGetCampaignsMappingToListOfMaps_thenListMappedCorrectly() {
        ParameterizedTypeReference<Collection<Map<String, Object>>> listOfElementsType = new ParameterizedTypeReference<Collection<Map<String, Object>>>() {
        };
        webClient.get()
            .uri("/campaigns")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(listOfElementsType)
            .value(mapResponseBody -> {
                assertThat(mapResponseBody).hasSizeGreaterThan(1);
            });
    }

    @Test
    void givenPreloadedData_whenGetNonExistingCampaign_thenNotFoundErrorWithProblemDetailsFormat() {
        webClient.get()
            .uri("/campaigns/99")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody(ProblemDetail.class)
            .value(problemDetailResponseBody -> {
                assertThat(problemDetailResponseBody.getStatus()).isEqualTo(404);
                assertThat(problemDetailResponseBody.getTitle()).isEqualTo("Not Found");
            });

    }

    @Test
    void whenCreateNewCampaign_thenIsCreated() {
        CampaignDto newCampaignBody = new CampaignDto(null, "CAMPAIGN-NEW-CODE", "Test - New Campaign 1", "Description of new test campaign 1", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CampaignDto.class)
            .value(newCampaign -> {
                assertThat(newCampaign.id()).isNotNull();
                assertThat(newCampaign.tasks()).isNotNull();
                assertThat(newCampaign.tasks()).isEmpty();
                assertThat(newCampaign.code()).isEqualTo(newCampaignBody.code());
                assertThat(newCampaign.name()).isEqualTo(newCampaignBody.name());
                assertThat(newCampaign.description()).isEqualTo(newCampaignBody.description());
            });
    }

    @Test
    void whenCreateNewCampaignWithDuplicatedCode_thenBadRequest() {
        CampaignDto newCampaignBody = new CampaignDto(null, "CAMPAIGN-DUPLICATED-CODE", "Test - New Campaign 2", "Description of new test campaign 2", null);

        webClient.post()
            .uri("/campaigns")
            .bodyValue(newCampaignBody)
            .exchange()
            .expectStatus()
            .isCreated();

        CampaignDto newDuplicatedCodeCampaignBody = new CampaignDto(null, "CAMPAIGN-DUPLICATED-CODE", "Test - New Campaign 3", "Description of new test campaign 3", null);

        webClient.post()
            .uri("/campaigns")
            .bodyValue(newDuplicatedCodeCampaignBody)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    void givenPreloadedData_whenUpdateExistingCampaign_thenOkWithSupportedFieldUpdated() {
        TaskDto taskBody = new TaskDto(null, null, "Test - Task", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null);
        Set<TaskDto> tasksListBody = Set.of(taskBody);
        CampaignDto updatedCampaignBody = new CampaignDto(null, "CAMPAIGN-CODE-UPDATED", "Test - Updated Campaign 1", "Description of updated test campaign 1", tasksListBody);

        webClient.put()
            .uri("/campaigns/2")
            .bodyValue(updatedCampaignBody)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CampaignDto.class)
            .value(dto -> {
                assertThat(dto.id()).isEqualTo(2L);
                assertThat(dto.code()).isNotEqualTo(updatedCampaignBody.code());
                assertThat(dto.name()).isEqualTo(updatedCampaignBody.name());
                assertThat(dto.description()).isEqualTo(updatedCampaignBody.description());
                assertThat(dto.tasks()).isNotEmpty()
                    .noneMatch(task -> task.name()
                        .equals(taskBody.name()));
            });
    }

    @Test
    void givenPreloadedData_whenUpdateExistingCampaignWithInvalidFields_thenBadRequest() {
        // null name
        CampaignDto nullNameCampaignBody = new CampaignDto(null, "CAMPAIGN-CODE-UPDATED", null, "Description of updated test campaign 2", null);

        webClient.put()
            .uri("/campaigns/2")
            .bodyValue(nullNameCampaignBody)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    void whenCreateNewCampaignWithContentTypeText_thenResponseIncludeAppropriateHeaders() {
        webClient.post()
            .uri("/campaigns")
            .contentType(MediaType.TEXT_PLAIN)
            .header("Custom-Header", "Custom value")
            .bodyValue("Test - New Campaign 3")
            .exchange()
            .expectHeader()
            .value(HttpHeaders.ACCEPT, headerValue -> headerValue.contains("application/json"))
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON);
    }

    @Test
    void whenCreateNewCampaignWithContentTypeText_thenUnsupportedMediaType() {
        webClient.post()
            .uri("/campaigns")
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValue("Test - New Campaign 3")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .expectHeader()
            .value(HttpHeaders.ACCEPT, headerValue -> headerValue.contains("application/json"));
    }

    @Test
    void whenCreateNewCampaignWithContentTypeText_thenClientErrorResponse() {
        webClient.post()
            .uri("/campaigns")
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValue("Test - New Campaign 3")
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }

    @Test
    void givenPreloadedData_whenCreateCampaign_thenResponseConsumeWith() {
        CampaignDto newCampaignBody = new CampaignDto(null, "CAMPAIGN-CONSUMEWITH", "Test - New Campaign - consumeWith", "Description of new test campaign", null);

        webClient.post()
            .uri("/campaigns")
            .bodyValue(newCampaignBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CampaignDto.class)
            .consumeWith(exchangeResult -> {
                assertThat(exchangeResult.getRequestHeaders()).extractingByKey(HttpHeaders.CONTENT_TYPE)
                    .asList()
                    .contains(MediaType.APPLICATION_JSON_VALUE);
                assertThat(exchangeResult.getResponseCookies()).isEmpty();
                assertThat(exchangeResult.getResponseBody()
                    .code()).isNotNull();
            });
    }
}
