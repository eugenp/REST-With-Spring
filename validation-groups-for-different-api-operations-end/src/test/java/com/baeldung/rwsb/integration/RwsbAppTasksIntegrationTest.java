package com.baeldung.rwsb.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rwsb.domain.model.TaskStatus;
import com.baeldung.rwsb.web.dto.TaskDto;
import com.baeldung.rwsb.web.dto.WorkerDto;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RwsbAppTasksIntegrationTest {

    @Autowired
    WebTestClient webClient;

    // GET - search

    @Test
    void givenPreloadedData_whenSearchTasksWithNameAndAssigneeId_thenOk() {
        webClient.get()
            .uri("/tasks?name=Task&assigneeId=1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.length()")
            .value(greaterThanOrEqualTo(1));
    }

    @Test
    void givenPreloadedData_whenSearchTasksWithoutFilter_thenOk() {
        webClient.get()
            .uri("/tasks")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.length()")
            .value(greaterThanOrEqualTo(4))
            .jsonPath("$..assignee.id")
            .value(hasItems(1))
            .jsonPath("$..assignee")
            .value(hasItem(nullValue()));
    }

    @Test
    void givenPreloadedData_whenSearchTasksWithName_thenOk() {
        Integer total = webClient.get()
            .uri("/tasks")
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .collectList()
            .block()
            .size();

        webClient.get()
            .uri("/tasks?name=1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.length()")
            .value(allOf(greaterThanOrEqualTo(1), lessThan(total)));
    }

    @Test
    void givenPreloadedData_whenSearchTasksWithAssigneeId_thenOk() {
        Integer total = webClient.get()
            .uri("/tasks")
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .collectList()
            .block()
            .size();

        webClient.get()
            .uri("/tasks?assigneeId=1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.length()")
            .value(allOf(greaterThanOrEqualTo(1), lessThan(total)))
            .jsonPath("$..assignee.id")
            .value(hasItem(1))
            .jsonPath("$..assignee")
            .value(not(hasItem(nullValue())));
    }

    // GET - by id

    @Test
    void givenPreloadedData_whenGetTask_thenOk() {
        webClient.get()
            .uri("/tasks/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(1L)
            .jsonPath("$.uuid")
            .isNotEmpty()
            .jsonPath("$.name")
            .isNotEmpty()
            .jsonPath("$.description")
            .isNotEmpty()
            .jsonPath("$.dueDate")
            .isNotEmpty()
            .jsonPath("$.status")
            .isNotEmpty()
            .jsonPath("$.campaignId")
            .isNotEmpty();
    }

    // POST - new

    @Test
    void whenCreateNewTask_thenCreatedWithoutModifyingAssociatedCampaignAndToDoStatus() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(4))
            .jsonPath("$.uuid")
            .isNotEmpty()
            .jsonPath("$.name")
            .isEqualTo("Test - Task X")
            .jsonPath("$.description")
            .isEqualTo("Description of task")
            .jsonPath("$.campaignId")
            .isEqualTo(1L)
            .jsonPath("$.status")
            .isEqualTo(TaskStatus.TO_DO.getLabel());

        webClient.get()
            .uri("/campaigns/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name")
            .isNotEmpty()
            .jsonPath("$.tasks.length()")
            .value(greaterThan(1))
            .jsonPath("$.tasks..name")
            .value(hasItem("Test - Task X"));
    }

    @Test
    void whenCreateNewTaskWithStatusInBody_thenCreatedWithDefaultToDoStatus() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X2", "Description of task 2", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 1L, null, 10);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(4))
            .jsonPath("$.uuid")
            .isNotEmpty()
            .jsonPath("$.status")
            .isEqualTo(TaskStatus.TO_DO.getLabel());
    }

    @Test
    void whenCreateNewTaskPresentingExistingId_thenCreatedWithoutUpdatingAssociatedExistingTask() {
        TaskDto newTaskBody = new TaskDto(1L, null, "Test - Task X3", "Description of task 3", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(4))
            .jsonPath("$.uuid")
            .isNotEmpty()
            .jsonPath("$.name")
            .isEqualTo("Test - Task X3")
            .jsonPath("$.description")
            .isEqualTo("Description of task 3")
            .jsonPath("$.campaignId")
            .isEqualTo(1L);

        webClient.get()
            .uri("/tasks/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name")
            .value(not("Test - Task X3"));
    }

    @Test
    void givenPreloadedData_whenCreateNewTaskUsingExistingUuid_thenServerError() {
        String existingUuid = webClient.get()
            .uri("/tasks/1")
            .exchange()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .uuid();

        assertThat(existingUuid).isNotNull();

        TaskDto newTaskBody = new TaskDto(null, existingUuid, "Test - Task X4", "Description of task 4", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void whenCreateNewTaskWithExistingWorker_thenCreatedWithNullWorker() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, new WorkerDto(1L, null, null, null), 10);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(4))
            .jsonPath("$.uuid")
            .isNotEmpty()
            .jsonPath("$.assignee")
            .isEqualTo(null);
    }

    @Test
    void whenCreateNewTaskWithNewWorker_thenCreatedWithoutCreatingNewWorker() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), null, 1L, new WorkerDto(null, "emailtest5@testemail.com", "First Name 5", "Last Name 5"), 10);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(4))
            .jsonPath("$.uuid")
            .isNotEmpty()
            .jsonPath("$.assignee")
            .isEqualTo(null);
    }

    @Test
    void whenCreateNewTaskPointingToNonExistingCampaign_thenServerError() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 99L, null, 10);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError()
            .expectBody()
            .jsonPath("$.error")
            .isNotEmpty();

        webClient.get()
            .uri("/campaigns/99")
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    // POST - new - validations

    @Test
    void whenCreateNewTaskWithInvalidScenarios_thenBadRequest() {

        // null name
        TaskDto nullNameTaskBody = new TaskDto(null, null, null, "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 1L, null, null);

        webClient.mutate()
            .responseTimeout(Duration.ofMillis(3000000))
            .build()
            .post()
            .uri("/tasks")
            .body(Mono.just(nullNameTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("name"));

        // blank name
        TaskDto blankNameTaskBody = new TaskDto(null, null, " ", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 1L, null, null);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(blankNameTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("name"));

        // short description
        TaskDto shortDescriptionTaskBody = new TaskDto(null, null, "A Name", "Desc.", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 1L, null, null);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(shortDescriptionTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("description"));

        // past dueDate
        TaskDto pastDueDateTaskBody = new TaskDto(null, null, "A Name", "Description of task", LocalDate.of(2000, 01, 01), TaskStatus.DONE, 1L, null, null);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(pastDueDateTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("dueDate"));

        // null campaignId
        TaskDto nullCampaignIdTaskBody = new TaskDto(null, null, "A Name", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null, null);
        //
        webClient.post()
            .uri("/tasks")
            .body(Mono.just(nullCampaignIdTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("campaignId"));

        // large estimatedHours
        TaskDto largeEstimatedHoursTaskBody = new TaskDto(null, null, "A Name", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 1L, null, 100);

        webClient.post()
            .uri("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(largeEstimatedHoursTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("estimatedHours"));
    }

    // PUT - update

    @Test
    void whenUpdateExistingTask_thenOkWithSupportedFieldUpdated() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, "changed-uuid", "Test - Task X", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 3L, null, 10);

        webClient.put()
            .uri("/tasks/" + newId)
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(newId)
            .jsonPath("$.uuid")
            .value(not("changed-uuid"))
            .jsonPath("$.name")
            .isEqualTo("Test - Task X")
            .jsonPath("$.description")
            .isEqualTo("Description of task")
            .jsonPath("$.dueDate")
            .isEqualTo("2030-06-01")
            .jsonPath("$.status")
            .isEqualTo(TaskStatus.DONE.getLabel())
            .jsonPath("$.campaignId")
            .isEqualTo(3L);

        webClient.get()
            .uri("/campaigns/3")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name")
            .isNotEmpty()
            .jsonPath("$.tasks.length()")
            .value(greaterThanOrEqualTo(1))
            .jsonPath("$.tasks..name")
            .value(hasItem("Test - Task X"));
    }

    @Test
    void givenPreloadedData_whenUpdateExistingTaskPointingToNonExistingCampaign_thenServerError() {
        TaskDto updatedTaskBody = new TaskDto(null, "changed-uuid", "Test - Task To Non Existing Campaign", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 99L, null, 10);

        webClient.put()
            .uri("/tasks/2")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError()
            .expectBody()
            .jsonPath("$.error")
            .isNotEmpty();

        webClient.get()
            .uri("/tasks/2")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name")
            .value(not("Test - Task To Non Existing Campaign"));
    }

    @Test
    void givenPreloadedData_whenUpdateNonExistingTask_thenNotFound() {
        TaskDto updatedTaskBody = new TaskDto(null, "changed-uuid", "Test - Task X", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 3L, null, 10);

        webClient.put()
            .uri("/tasks/99")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody()
            .jsonPath("$.error")
            .isNotEmpty();
    }

    @Test
    void whenUpdateTaskWithExistingWorker_thenOk() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5.2", "Description of task 5.2", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), TaskStatus.TO_DO, 1L, new WorkerDto(1L, null, null, null), 10);

        webClient.put()
            .uri("/tasks/" + newId)
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.assignee.firstName")
            .exists();
    }

    @Test
    void whenUpdateTaskWithNonExistingWorker_thenServerError() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), TaskStatus.TO_DO, 1L, new WorkerDto(99L, null, null, null), 10);

        webClient.put()
            .uri("/tasks/" + newId)
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void whenUpdateTaskWithExistingWorker_thenUpdatedPointingToUnmodifiedWorker() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), TaskStatus.TO_DO, 1L, new WorkerDto(1L, "emailtest6@testemail.com", "First Name 6", "Last Name 6"), 10);

        webClient.put()
            .uri("/tasks/" + newId)
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name")
            .isEqualTo("Test - Task X6")
            .jsonPath("$.assignee.id")
            .isEqualTo(1L)
            .jsonPath("$.assignee.email")
            .value(not("emailtest6@testemail.com"))
            .jsonPath("$.assignee.firstName")
            .value(not("First Name 6"))
            .jsonPath("$.assignee.lastName")
            .value(not("Last Name 6"));
    }

    // PUT - update - validations

    @Test
    void whenUpdateWithInvalidScenarios_thenBadRequest() {
        // null status
        TaskDto updatedTaskBody = new TaskDto(null, "any-uuid", "Test - Task X", "Description of task", LocalDate.of(2030, 06, 01), null, 3L, null, null);

        webClient.put()
            .uri("/tasks/1")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest();

        // null name
        TaskDto nullNameTaskBody = new TaskDto(null, "any-uuid", null, "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 3L, null, null);

        webClient.put()
            .uri("/tasks/1")
            .body(Mono.just(nullNameTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest();

        // null campaignId
        TaskDto nullCampaignIdTaskBody = new TaskDto(null, "any-uuid", "Test - Task X", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, null, null, null);

        webClient.put()
            .uri("/tasks/1")
            .body(Mono.just(nullCampaignIdTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest();

        // null uuid and assignee - valid
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null, null);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto nullUuidTaskBody = new TaskDto(null, null, "Test - Task Valid X", "Description of valid task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 3L, null, null);

        webClient.put()
            .uri("/tasks/" + newId)
            .body(Mono.just(nullUuidTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name")
            .isEqualTo("Test - Task Valid X");
    }

    // PUT - update status

    @Test
    void whenUpdateStatusForExistingTask_thenOkWithOnlyStatusUpdated() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Status Task X", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.IN_PROGRESS, 1L, null, 10);

        webClient.put()
            .uri("/tasks/" + newId + "/status")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(TaskStatus.IN_PROGRESS.getLabel())
            .jsonPath("$.name")
            .value((not("Test - Status Task X")));
    }

    @Test
    void whenUpdateStatusWithJustStatusInBody_thenOk() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, null, null, null, null, TaskStatus.ON_HOLD, null, null, 10);

        webClient.put()
            .uri("/tasks/" + newId + "/status")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(TaskStatus.ON_HOLD.getLabel())
            .jsonPath("$.name")
            .isNotEmpty();
    }

    // PUT - update status - validations

    @Test
    void givenPreloadedData_whenUpdateStatusWithNullStatus_thenBadRequest() {
        // to check input validations not messing up things here
        TaskDto nullStatusTaskBody = new TaskDto(null, null, "Test - Status Task X2", null, null, null, null, null, null);

        webClient.put()
            .uri("/tasks/2/status")
            .body(Mono.just(nullStatusTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("status"));
    }

    // PUT - update assignee

    @Test
    void whenUpdateAssigneeForExistingTask_thenOkWithOnlyAssigneeUpdated() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null, 10);

        Long newId = webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(TaskDto.class)
            .getResponseBody()
            .blockFirst()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Assignee Task X", "Description of task", LocalDate.of(2030, 06, 01), null, 1L, new WorkerDto(1L, null, null, null), 10);

        webClient.put()
            .uri("/tasks/" + newId + "/assignee")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.status")
            .isNotEmpty()
            .jsonPath("$.name")
            .value((not("Test - Assignee Task X")))
            .jsonPath("$.assignee.id")
            .isEqualTo(1L)
            .jsonPath("$.assignee.firstName")
            .isNotEmpty()
            .jsonPath("$.assignee.email")
            .isNotEmpty();
    }

    // PUT - update assignee - validations

    @Test
    void givenPreloadedData_whenUpdateAssigneeWithNullAssignee_thenBadRequest() {
        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Assignee Task X", "Description of task", LocalDate.of(2030, 06, 01), null, 1L, null, 10);

        webClient.put()
            .uri("/tasks/1")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("status"));
    }

    @Test
    void givenPreloadedData_whenUpdateAssigneeWithAssigneeWithNoId_thenBadRequest() {
        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Assignee Task X", "Description of task", LocalDate.of(2030, 06, 01), null, 1L, new WorkerDto(null, null, null, null), 10);

        webClient.put()
            .uri("/tasks/1")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.errors..field")
            .value(hasItem("status"));
    }
}
