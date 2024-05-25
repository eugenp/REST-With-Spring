package com.baeldung.rws.contract.simplified;

import static com.baeldung.rws.commons.contract.SimpleRequestBodyBuilder.fromResource;
import static java.util.Map.entry;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.baeldung.rws.commons.contract.SimpleContractWebTestClient;
import com.baeldung.rws.domain.model.TaskStatus;
import com.baeldung.rws.web.dto.TaskDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractTasksSimplifiedApiIntegrationTest {

    @Autowired
    SimpleContractWebTestClient webClient;

    @Value("classpath:task.json")
    Resource resource;

    @Test
    void whenCreateNewTask_thenSuccess() throws Exception { // @formatter:off
        String taskJson = fromResource(this.resource).build();

        webClient.create("/tasks", taskJson);
    } // @formatter:on

    @Test
    void whenCreateNewTask_thenSuccessWithExpectedFields() throws Exception { // @formatter:off
        String taskJson = fromResource(this.resource).build();

        webClient.create("/tasks", taskJson)
            .containsFields("id", "name", "campaignId", "dueDate")
            .fieldsMatch(
                entry("name", equalTo("Test - New Task 1")),
                entry("campaignId", greaterThan(0)),
                entry("dueDate", equalTo("2050-12-30")));
    } // @formatter:on

    @Test
    void whenCreateNewTaskWithBlankName_thenBadRequest() throws Exception { // @formatter:off
        String taskJson = fromResource(this.resource)
            .with("name", "")
            .build();

        webClient.requestWithResponseStatus("/tasks", HttpMethod.POST, taskJson, HttpStatus.BAD_REQUEST);
    } // @formatter:on

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseContainsFields() { // @formatter:off
        webClient.get("/tasks/1")
        .containsFields("name")
            .fieldsMatch(
                entry("id", equalTo(1)),
                entry("status", equalTo("To Do")),
                entry("description", containsString("Description")));
    } // @formatter:on

    // GET - search

    @Test
    void givenPreloadedData_whenSearchTasksWithNameAndAssigneeId_thenOk() {
        webClient.get("/tasks?name=Task&assigneeId=1")
            .fieldsMatch(entry("length()", greaterThanOrEqualTo(1)));
    }

    @Test
    void givenPreloadedData_whenSearchTasksWithoutFilter_thenOk() {
        webClient.get("/tasks")
            .fieldsMatch(entry("length()", greaterThanOrEqualTo(4)))
            .listFieldsMatch(entry("assignee.id", hasItems(1)), entry("assignee", hasItem(nullValue())));
    }

    // GET - by id

    @Test
    void givenPreloadedData_whenGetTask_thenOk() {
        webClient.get("/tasks/1")
            .containsFields("id", "uuid", "name", "description", "dueDate", "status", "campaignId");
    }

    // POST - new

    @Test
    void whenCreateNewTask_thenCreatedWithoutModifyingAssociatedCampaignAndToDoStatus() throws Exception { // @formatter:off
        String newTaskBody = fromResource(this.resource)
            .with("name", "Test - Task X2")
            .with("description", "Description of task")
            .withNull("status")
            .with("campaignId", 1L)
            .build(); 

        webClient.create("/tasks", newTaskBody)
            .fieldsMatch(
                entry("id", greaterThan(4)),
                entry("uuid", not(emptyOrNullString())),
                entry("name", equalTo("Test - Task X2")),
                entry("description", equalTo("Description of task")),
                entry("campaignId", equalTo(1)),
                entry("status", equalTo(TaskStatus.TO_DO.getLabel())));

        webClient.get("/campaigns/1")
            .fieldsMatch(
                entry("name", not(emptyOrNullString())),
                entry("tasks.length()", greaterThan(1)),
                entry("tasks..name", hasItem("Test - Task X2")));
    } // @formatter:on

    @Test
    void whenCreateNewTaskPointingToNonExistingCampaign_thenBadRequest() throws Exception { // @formatter:off
        String newTaskBody = fromResource(this.resource)
            .with("campaignId", 99L)
            .build();

        webClient.requestWithResponseStatus("/tasks",
            HttpMethod.POST,
            newTaskBody,
            HttpStatus.BAD_REQUEST)
              .containsFields("title");
    } // @formatter:on

    @Test
    void whenCreateNewTaskPointingToNullCampaign_thenBadRequest() throws Exception { // @formatter:off
        String newTaskBody = fromResource(this.resource)
            .withNull("campaignId")
            .build();

        webClient.requestWithResponseStatus("/tasks",
            HttpMethod.POST,
            newTaskBody,
            HttpStatus.BAD_REQUEST);
    } // @formatter:on

    // POST - new - validations

    @Test
    void whenCreateNewTaskWithNegativeCampaignId_thenBadRequest() throws Exception { // @formatter:off
        // blank name
        String blankNameTaskBody = fromResource(this.resource)
            .with("name", "  ")
            .build();
        webClient.requestWithResponseStatus("/tasks",
            HttpMethod.POST,
            blankNameTaskBody,
            HttpStatus.BAD_REQUEST);
        
        // short description
        String shortDescriptionTaskBody = fromResource(this.resource)
            .with("description", "short")
            .build();
        webClient.requestWithResponseStatus("/tasks",
            HttpMethod.POST,
            shortDescriptionTaskBody,
            HttpStatus.BAD_REQUEST);
        
        // past date
        String pastDueDateTaskBody = fromResource(this.resource)
            .with("dueDate", "1990-01-01")
            .build();
        webClient.requestWithResponseStatus("/tasks",
            HttpMethod.POST,
            pastDueDateTaskBody,
            HttpStatus.BAD_REQUEST);
    } // @formatter:on

    // PUT - update

    @Test
    void whenUpdateExistingTask_thenOkWithSupportedFieldUpdated() throws Exception { // @formatter:off
        String newTaskBody = fromResource(this.resource)
            .with("name", "Test - Task X6")
            .with("description", "Description of task 6")
            .with("dueDate", "2050-12-31")
            .build();

        Long newId = webClient.extractResponseBody("/tasks",
            HttpMethod.POST,
            TaskDto.class,
            newTaskBody)
              .id();

        String updatedTaskBody = fromResource(this.resource)
            .with("uuid", "changed-uuid")
            .with("name", "Test - Task X7")
            .with("description", "Description of task 7")
            .with("dueDate", "2045-06-15")
            .with("status", "In Progress")
            .with("campaignId", 2L)
            .build();

        webClient.put("/tasks/" + newId, updatedTaskBody)
            .fieldsMatch(
                entry("id", equalTo(newId.intValue())),
                entry("uuid", not("changed-uuid")),
                entry("name", equalTo("Test - Task X7")),
                entry("description", equalTo("Description of task 7")),
                entry("dueDate", equalTo("2045-06-15")),
                entry("status", equalTo(TaskStatus.IN_PROGRESS.getLabel())),
                entry("campaignId", equalTo(2)));

        webClient.get("/campaigns/2")
            .fieldsMatch(
                entry("name", not(emptyOrNullString())),
                entry("tasks.length()", greaterThanOrEqualTo(1)),
                entry("tasks..name", hasItem("Test - Task X7")));
    } // @formatter:on

    @Test
    void givenPreloadedData_whenUpdateExistingTaskPointingToNonExistingCampaign_thenBadRequest() throws Exception { // @formatter:off
        String updatedTaskBody = fromResource(this.resource)
            .with("campaignId", 99L)
            .build();

        webClient.requestWithResponseStatus("/tasks/2",
            HttpMethod.PUT,
            updatedTaskBody,
            HttpStatus.BAD_REQUEST)
                .fieldsMatch(
                    entry("title", not(emptyOrNullString())));
        
        webClient.get("/tasks/2")
            .fieldsMatch(
                entry("name", not("Test - Task To Non Existing Campaign")));
    } // @formatter:on

    @Test
    void givenPreloadedData_whenUpdateNonExistingTask_thenNotFound() throws Exception { // @formatter:off
        String updatedTaskBody = fromResource(this.resource).build();

        webClient.requestWithResponseStatus("/tasks/99",
            HttpMethod.PUT,
            updatedTaskBody,
            HttpStatus.NOT_FOUND);
    } // @formatter:on

    // PUT - update status

    @Test
    void whenUpdateStatusForExistingTask_thenOkWithOnlyStatusUpdated() throws Exception { // @formatter:off
        String newTaskBody = fromResource(this.resource).build();

        Long newId = webClient.extractResponseBody("/tasks", HttpMethod.POST, TaskDto.class, newTaskBody)
            .id();
        
        String updatedTaskBody = fromResource(this.resource)
            .with("name", "Test - Task X9")
            .with("description", "Description of task 9")
            .with("dueDate", "2045-06-15")
            .with("status", "In Progress")
            .with("campaignId", 2L)
            .build();

        webClient.put("/tasks/" + newId + "/status", updatedTaskBody)
            .fieldsMatch(
                entry("status", equalTo(TaskStatus.IN_PROGRESS.getLabel())),
                entry("name", not("Test - Task X9")),
                entry("description", not("Description of task 9")),
                entry("dueDate", not("2045-06-15")),
                entry("campaignId", not(2)));
    } // @formatter:on

    // PUT - update assignee

    @Test
    void whenUpdateAssigneeForExistingTask_thenOkWithOnlyAssigneeUpdated() throws Exception { // @formatter:off
        String newTaskBody = fromResource(this.resource).build();

        Long newId = webClient.extractResponseBody("/tasks",
            HttpMethod.POST,
            TaskDto.class,
            newTaskBody)
                .id();
        
        String existingWorker = """
            {
                "id": 1
            }
             """;
        String updatedTaskBody = fromResource(this.resource)
            .with("name", "Test - Task X10")
            .with("description", "Description of task 10")
            .with("dueDate", "2045-06-15")
            .with("status", "In Progress")
            .with("campaignId", 2L)
            .withJsonString("assignee", existingWorker)
            .build();

        webClient.put("/tasks/" + newId + "/assignee", updatedTaskBody)
            .fieldsMatch(
                entry("status", not("In Progress")),
                entry("name", not("Test - Task X10")),
                entry("assignee.id", equalTo(1)),
                entry("assignee.firstName", not(emptyOrNullString())),
                entry("assignee.email", not(emptyOrNullString())));
    } // @formatter:on
}
