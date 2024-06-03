package com.baeldung.rwsb.contract.spring;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;

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

import com.baeldung.rwsb.domain.model.TaskStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractTasksApiIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Value("classpath:task.json")
    Resource resource;

    @Test
    void givenPreloadedData_whenGetSearchTasksUsingCampaignIdPath_thenResponseContainsLessEntries() { // @formatter:off
        // campaign 1 id: ebcbeadc-c7de-45ec-8c45-7d23a2554cc6
        webClient.get()
            .uri("/campaigns/1/tasks")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$._embedded.taskDtoList.length()")
            //            .value(equalTo(3));
            .value(greaterThanOrEqualTo(3));
    } // @formatter:on

    @Test
    void givenPreloadedData_whenGetSearchTasksUsingNonExistingCampaignIdPath_thenNoneRetrieved() { // @formatter:off
        webClient.get()
            .uri("/campaigns/99/tasks")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$._embedded")
            .doesNotExist();
    } // @formatter:on

    // GET - single

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseContainsFields() { // @formatter:off
        webClient.get()
            .uri("/tasks/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .value(equalTo(1L), Long.class)
            .jsonPath("$.name")
            .exists()
            .jsonPath("$.status")
            .isEqualTo(TaskStatus.TO_DO.toValue())
            .jsonPath("$.description")
            .value(containsString("Description"));
    } // @formatter:on

    @Test
    void createNewTask_withValidTaskJsonInput_thenSuccess() throws Exception {
        String taskJson = generateTaskInput();

        webClient.post()
            .uri("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(taskJson)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .exists()
            .jsonPath("$.name")
            .isEqualTo("Test - Template Task Name")
            .jsonPath("$.campaignId")
            .value(greaterThan(0))
            .jsonPath("$.dueDate")
            .isEqualTo("2050-12-31");
    }

    private String generateTaskInput() throws Exception {
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }

    // HATEOAS
    @Test
    void givenPreloadedData_whenSearchTasks_thenResponseWithHateoas() {
        webClient.get()
            .uri("/tasks?name=Task&assigneeId=1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$._embedded.taskDtoList.._links.assignee.href")
            .value(hasItem(containsString("/employees/")))
            .jsonPath("$._links.self.deprecation")
            .isEqualTo("https://my-site/further-info-on-deprecation")
            .jsonPath("$._links.searchTasksByCampaign.href")
            .value(containsString("/campaigns/{campaignId}/tasks?name=Task&assigneeId=1"))
            .jsonPath("$._links.searchTasksByCampaign.templated")
            .isEqualTo(true);
    }
}