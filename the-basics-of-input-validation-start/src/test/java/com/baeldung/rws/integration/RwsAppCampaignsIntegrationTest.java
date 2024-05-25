package com.baeldung.rws.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import java.time.LocalDate;
import java.util.Set;

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
public class RwsAppCampaignsIntegrationTest {

    @Autowired
    WebTestClient webClient;

    // GET - by id

    @Test
    void givenPreloadedData_whenGetCampaign_thenOk() {
        webClient.get()
            .uri("/campaigns/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(1L)
            .jsonPath("$.code")
            .isNotEmpty()
            .jsonPath("$.name")
            .isNotEmpty()
            .jsonPath("$.description")
            .isNotEmpty()
            .jsonPath("$.tasks..name")
            .isNotEmpty();
    }

    // GET - list

    @Test
    void givenPreloadedData_whenGetCampaigns_thenOk() {
        webClient.get()
            .uri("/campaigns")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.length()")
            .value(greaterThan(1))
            .jsonPath("$..code")
            .isNotEmpty();
    }

    // POST - new

    @Test
    void whenCreateNewCampaign_thenCreatedWithNoTasks() {
        Set<TaskDto> tasksBody = Set.of(new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null, 1));
        CampaignDto newCampaignBody = new CampaignDto(null, "TEST-CAMPAIGN-NEW-1", "Test - New Campaign 1", "Description of new test campaign 1", tasksBody);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(3))
            .jsonPath("$.code")
            .isEqualTo("TEST-CAMPAIGN-NEW-1")
            .jsonPath("$.name")
            .isEqualTo("Test - New Campaign 1")
            .jsonPath("$.description")
            .isEqualTo("Description of new test campaign 1")
            .jsonPath("$.tasks")
            .isEmpty();
    }

    @Test
    void whenCreateNewCampaignPresentingExistingId_thenCreatedWithoutUpdatingExistingCampaign() {
        CampaignDto newCampaignBody = new CampaignDto(1L, "TEST-CAMPAIGN-NEW-2", "Test - New Campaign 2", "Description of new test campaign 2", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(3))
            .jsonPath("$.code")
            .isEqualTo("TEST-CAMPAIGN-NEW-2")
            .jsonPath("$.name")
            .isEqualTo("Test - New Campaign 2")
            .jsonPath("$.description")
            .isEqualTo("Description of new test campaign 2");
    }

    @Test
    void whenCreateNewCampaignWithDuplicatedCode_thenServerError() {
        CampaignDto newCampaignBody = new CampaignDto(null, "TEST-CAMPAIGN-NEW-3", "Test - New Campaign 3", "Description of new test campaign 3", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isCreated();

        CampaignDto newDuplicatedCodeCampaignBody = new CampaignDto(null, "TEST-CAMPAIGN-NEW-3", "Test - New Campaign 4", "Description of new test campaign 4", null);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newDuplicatedCodeCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void whenCreateNewCampaignPointingToExistingTasks_thenCreatedWithNoTasks() {
        Set<TaskDto> tasksBody = Set.of(new TaskDto(1L, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null, 1), new TaskDto(2L, "any-uuid", null, null, null, null, null, null, 1));
        CampaignDto newCampaignBody = new CampaignDto(null, "TEST-CAMPAIGN-NEW-5", "Test - New Campaign 5", "Description of new test campaign 5", tasksBody);

        webClient.post()
            .uri("/campaigns")
            .body(Mono.just(newCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(3))
            .jsonPath("$.code")
            .isEqualTo("TEST-CAMPAIGN-NEW-5")
            .jsonPath("$.tasks")
            .isEmpty();
    }

    // PUT - update

    @Test
    void givenPreloadedData_whenUpdateExistingCampaign_thenOkWithSupportedFieldUpdated() {
        Set<TaskDto> tasksBody = Set.of(new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null, 1));
        CampaignDto updatedCampaignBody = new CampaignDto(null, "TEST-CAMPAIGN-UPDATED-1", "Test - Updated Campaign 2", "Description of updated test campaign 2", tasksBody);

        webClient.put()
            .uri("/campaigns/2")
            .body(Mono.just(updatedCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(2L)
            .jsonPath("$.code")
            .value(not("TEST-CAMPAIGN-UPDATED-1"))
            .jsonPath("$.name")
            .isEqualTo("Test - Updated Campaign 2")
            .jsonPath("$.description")
            .isEqualTo("Description of updated test campaign 2")
            .jsonPath("$.tasks")
            .isNotEmpty()
            .jsonPath("$.tasks..name")
            .value(not(hasItem("Test - Task X")));
    }

    @Test
    void givenPreloadedData_whenUpdateNonExistingCampaign_thenNotFound() {
        CampaignDto updatedCampaignBody = new CampaignDto(null, null, "Test - Updated Campaign 2", "Description of updated test campaign 2", null);

        webClient.put()
            .uri("/campaigns/99")
            .body(Mono.just(updatedCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody()
            .jsonPath("$.error")
            .isNotEmpty();
    }

    @Test
    void givenPreloadedData_whenUpdateExistingCampaignUsingExistingTask_thenTaskNotSwitchedToCampaign() {
        // create Task assigning it to Campaign 1
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Campaign Task X", "Description of Campaign task", LocalDate.of(2030, 01, 01), null, 1L, null, 1);

        TaskDto newTask = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst();

        Long newTaskId = newTask.id();

        assertThat(newTaskId).isPositive();

        Set<TaskDto> tasksBody = Set.of(newTask);
        CampaignDto updatedCampaignBody = new CampaignDto(null, "TEST-CAMPAIGN-UPDATED-3", "Test - Updated Campaign 3", "Description of updated test campaign 3", tasksBody);

        webClient.put()
            .uri("/campaigns/2")
            .body(Mono.just(updatedCampaignBody), CampaignDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(2L)
            .jsonPath("$.name")
            .isEqualTo("Test - Updated Campaign 3")
            .jsonPath("$.tasks..id")
            .value(not(hasItem(newTaskId)));

        webClient.get()
            .uri("/tasks/" + newTaskId)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.campaignId")
            .isEqualTo(1L);
    }
}
