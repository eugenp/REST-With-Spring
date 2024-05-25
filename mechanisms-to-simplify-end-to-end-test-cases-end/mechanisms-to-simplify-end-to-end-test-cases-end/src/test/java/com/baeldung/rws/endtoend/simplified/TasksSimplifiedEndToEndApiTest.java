package com.baeldung.rws.endtoend.simplified;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.baeldung.rws.commons.endtoend.client.SimpleWebTestClient;
import com.baeldung.rws.domain.model.TaskStatus;
import com.baeldung.rws.endtoend.simplified.utils.TaskDtoSpec;
import com.baeldung.rws.web.dto.TaskDto;
import com.baeldung.rws.web.dto.WorkerDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TasksSimplifiedEndToEndApiTest {

    @Autowired
    SimpleWebTestClient webClient;

    // GET - by id

    @Test
    void givenPreloadedData_whenGetTask_thenOk() {
        webClient.get("/tasks/1", TaskDto.class)
            .valueMatches(new TaskDtoSpec(equalTo(1L), any(String.class), any(String.class), any(String.class), any(LocalDate.class), any(TaskStatus.class), any(Long.class), anyOf(nullValue(WorkerDto.class), any(WorkerDto.class))));
    }

    // POST - new

    @Test
    void whenCreateNewTask_thenCreatedWithoutModifyingAssociatedCampaignAndToDoStatus() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X14", "Description of task", LocalDate.of(2030, 01, 01), null, 1L, null);

        webClient.create("/tasks", newTaskBody)
            .valueMatches(new TaskDtoSpec(greaterThan(4L), not(emptyOrNullString()), equalTo("Test - Task X14"), equalTo("Description of task"), any(LocalDate.class), equalTo(TaskStatus.TO_DO), equalTo(1L), nullValue(WorkerDto.class)));
    }

    // PUT - update

    @Test
    void whenUpdateExistingTask_thenOkWithSupportedFieldUpdated() {
        TaskDto newTaskBody = new TaskDto(null, null, "Test - Task X17", "Description of task 5", LocalDate.of(2030, 01, 01), null, 1L, null);
        Long newId = webClient.create("/tasks", newTaskBody)
            .getWrappedBodySpec()
            .returnResult()
            .getResponseBody()
            .id();

        TaskDto updatedTaskBody = new TaskDto(null, "changed-uuid", "Test - Task X18", "Description of task", LocalDate.of(2030, 06, 01), TaskStatus.DONE, 2L, null);

        TaskDtoSpec expected = new TaskDtoSpec(equalTo(newId), not(equalTo(updatedTaskBody.uuid())), equalTo(updatedTaskBody.name()), equalTo(updatedTaskBody.description()), equalTo(updatedTaskBody.dueDate()), equalTo(updatedTaskBody.status()),
            equalTo(updatedTaskBody.campaignId()), nullValue(WorkerDto.class));
        webClient.put("/tasks/" + newId, updatedTaskBody)
            .valueMatches(expected);
    }

}