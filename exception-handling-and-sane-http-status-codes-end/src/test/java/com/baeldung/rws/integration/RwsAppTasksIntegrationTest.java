package com.baeldung.rws.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rws.domain.model.TaskStatus;
import com.baeldung.rws.web.dto.TaskDto;
import com.baeldung.rws.web.dto.WorkerDto;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RwsAppTasksIntegrationTest {

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
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), null, 1L, null);

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
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X2", "Description of task 2", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 1L, null);

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
        TaskDto newTaskBody = new TaskDto(1L, null, "Test - Task X3", "Description of task 3", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto newTaskBody = new TaskDto(null, existingUuid, "Test - Task X4", "Description of task 4", LocalDate.of(2030, 01, 01), null, 1L, null);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void whenCreateNewTaskWithExistingWorker_thenCreatedWithNullWorker() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, new WorkerDto(1L, "emailtest5@testemail.com", "First Name 5", "Last Name 5"));

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
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), null, 1L, new WorkerDto(null, "emailtest5@testemail.com", "First Name 5", "Last Name 5"));

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
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, 99L, null);

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

    @Test
    void whenCreateNewTaskPointingToNullCampaign_thenServerError() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null);

        webClient.post()
            .uri("/tasks")
            .body(Mono.just(newTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    // PUT - update

    @Test
    void whenUpdateExistingTask_thenOkWithSupportedFieldUpdated() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto updatedTaskBody = new TaskDto(null, "changed-uuid", "Test - Task X", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 3L, null);

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
        TaskDto updatedTaskBody = new TaskDto(null, "changed-uuid", "Test - Task To Non Existing Campaign", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 99L, null);

        webClient.put()
            .uri("/tasks/2")
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is4xxClientError()
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
        TaskDto updatedTaskBody = new TaskDto(null, "changed-uuid", "Test - Task X", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 3L, null);

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
    void whenUpdateTaskWithNewWorker_thenServerError() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), TaskStatus.TO_DO, 1L, new WorkerDto(null, "emailtest6@testemail.com", "First Name 6", "Last Name 6"));

        webClient.put()
            .uri("/tasks/" + newId)
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void whenUpdateTaskWithNonExistingWorker_thenServerError() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), TaskStatus.TO_DO, 1L, new WorkerDto(99L, "emailtest6@testemail.com", "First Name 6", "Last Name 6"));

        webClient.put()
            .uri("/tasks/" + newId)
            .body(Mono.just(updatedTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }

    @Test
    void whenUpdateTaskWithExistingWorker_thenUpdatedPointingToUnmodifiedWorker() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Task X6", "Description of task 6", LocalDate.of(2030, 01, 01), TaskStatus.TO_DO, 1L, new WorkerDto(1L, "emailtest6@testemail.com", "First Name 6", "Last Name 6"));

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

    // PUT - update status

    @Test
    void whenUpdateStatusForExistingTask_thenOkWithOnlyStatusUpdated() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Status Task X", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.IN_PROGRESS, 1L, null);

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
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto updatedTaskBody = new TaskDto(null, null, null, null, null, TaskStatus.ON_HOLD, null, null);

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

    @Test
    void givenPreloadedData_whenUpdateStatusWithNullStatus_thenServerError() {
        TaskDto nullStatusTaskBody = new TaskDto(null, null, "Test - Status Task X2", null, null, null, null, null);

        webClient.put()
            .uri("/tasks/2/status")
            .body(Mono.just(nullStatusTaskBody), TaskDto.class)
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    // PUT - update assignee

    @Test
    void whenUpdateAssigneeForExistingTask_thenOkWithOnlyAssigneeUpdated() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X5", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);

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

        TaskDto updatedTaskBody = new TaskDto(null, null, "Test - Assignee Task X", "Description of task", LocalDate.of(2030, 06, 01), null, 1L, new WorkerDto(1L, null, null, null));

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

    // Lesson test cases

    @Test
    void whenGetNonExistingTask_then404WithErrorResponseFields() {
        webClient.get()
            .uri("/tasks/99")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(404)
            .jsonPath("$.message")
            .isEqualTo("Couldn't find the requested Task")
            .jsonPath("$.exception")
            .exists()
            .jsonPath("$.trace")
            .exists()
            .jsonPath("$.error")
            .isEqualTo("Not Found");
    }

    @Test
    void whenGetNonExistingEndpoint_then404WithErrorResponseFields() {
        webClient.get()
            .uri("/other")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(404)
            .jsonPath("$.error")
            .isEqualTo("Not Found");
    }

    @Test
    void whenInvalidMethod_then405WithAllowHeader() {
        webClient.put()
            .uri("/tasks")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.METHOD_NOT_ALLOWED)
            .expectHeader()
            .exists(HttpHeaders.ALLOW)
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(405)
            .jsonPath("$.message")
            .isEqualTo("Method 'PUT' is not supported.")
            .jsonPath("$.exception")
            .exists()
            .jsonPath("$.trace")
            .exists()
            .jsonPath("$.error")
            .isEqualTo("Method Not Allowed");
    }

    @Test
    void whenInvalidContentType_then415WithAcceptHeader() {
        webClient.post()
            .uri("/tasks")
            .contentType(MediaType.APPLICATION_XML)
            .bodyValue("{ \"name\": \"Invalid Content-Type\", \"campaignId\": 1 }")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .expectHeader()
            .exists(HttpHeaders.ACCEPT)
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(415)
            .jsonPath("$.message")
            .exists()
            .jsonPath("$.exception")
            .exists()
            .jsonPath("$.trace")
            .exists()
            .jsonPath("$.error")
            .isEqualTo("Unsupported Media Type");
    }

    @Test
    void whenPutInvalidTask_then400WithErrorResponseFields() {
        TaskDto updatedToNonExistingCampaign = new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), null, 99L, null);

        webClient.put()
            .uri("/tasks/1")
            .body(Mono.just(updatedToNonExistingCampaign), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(400)
            .jsonPath("$.message")
            .isEqualTo("Associated entity not found: Unable to find com.baeldung.rws.domain.model.Campaign with id 99")
            .jsonPath("$.exception")
            .exists()
            .jsonPath("$.trace")
            .exists()
            .jsonPath("$.error")
            .isEqualTo("Bad Request");
    }

    @Test
    void whenPutInvalidTask2_then400WithErrorResponseFields() {
        TaskDto updatedToNonExistingWorker = new TaskDto(null, null, "Test - Task X", "Description of task", LocalDate.of(2030, 01, 01), null, 1L, new WorkerDto(99L, null, null, null));

        webClient.put()
            .uri("/tasks/1")
            .body(Mono.just(updatedToNonExistingWorker), TaskDto.class)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("$.status")
            .isEqualTo(400)
            .jsonPath("$.message")
            .isEqualTo("Associated entity not found: Unable to find com.baeldung.rws.domain.model.Worker with id 99")
            .jsonPath("$.exception")
            .exists()
            .jsonPath("$.trace")
            .exists()
            .jsonPath("$.error")
            .isEqualTo("Bad Request");
    }

}
