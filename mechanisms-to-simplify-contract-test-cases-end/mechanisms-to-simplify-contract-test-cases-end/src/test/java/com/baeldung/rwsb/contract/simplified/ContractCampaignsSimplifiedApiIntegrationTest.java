package com.baeldung.rwsb.contract.simplified;

import static com.baeldung.rwsb.commons.contract.SimpleRequestBodyBuilder.fromResource;
import static java.util.Map.entry;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.baeldung.rwsb.commons.contract.SimpleContractWebTestClient;
import com.baeldung.rwsb.commons.contract.SimpleRequestBodyBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractCampaignsSimplifiedApiIntegrationTest {

    @Autowired
    SimpleContractWebTestClient webClient;

    @Value("classpath:campaign-template.json")
    Resource resource;

    @Value("classpath:task.json")
    Resource taskResource;

    // GET - single

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseContainsFields() {
        webClient.get("/campaigns/1")
            .containsFields("id", "code", "name", "description", "tasks..name");

    }

    // GET - list

    @Test
    void givenPreloadedData_whenGetCampaignsUsingTuples_thenResponseFieldsMatch() { // @formatter:off
        // We can use a fluent API to use the wrapper methods that are more suitable for the validations we want to run
        webClient.get("/campaigns")
            .fieldsMatch(entry("length()", greaterThanOrEqualTo(2)))
            .listFieldsMatch(
                entry("code", hasItems("C1", "C2", "C3")),
                entry("tasks..name", hasItems("Task 1", "Task 2", "Task 3", "Task 4")));
    } // @formatter:on

    // POST - create

    @Test
    void whenCreateNewCampaign_thenCreatedAndResponseFieldsMatch() throws Exception {// @formatter:off
        String newCampaignBody = baseCampaignInput()
            .with("code", "TEST-C-S-CAMPAIGN-NEW-1")
            .with("name", "Test - New JSON Campaign 1")
            .with("description", "Description of new JSON test campaign 1")
            .build();

        webClient.create("/campaigns", newCampaignBody)
            .fieldsMatch(
                entry("id", greaterThan(3)),
                entry("tasks", empty()),
                entry("code", equalTo("TEST-C-S-CAMPAIGN-NEW-1")),
                entry("name", equalTo("Test - New JSON Campaign 1")),
                entry("description", equalTo("Description of new JSON test campaign 1")));
    } // @formatter:on

    // POST - new - validations

    @Test
    void whenCreateNewCampaignWithoutRequiredCodeField_thenBadRequest() throws Exception {
        // null code
        String nullCodeCampaignBody = baseCampaignInput().withNull("code")
            .build();

        webClient.requestWithResponseStatus("/campaigns", HttpMethod.POST, nullCodeCampaignBody, HttpStatus.BAD_REQUEST);
    }

    // PUT - update

    @Test
    void givenPreloadedData_whenUpdateExistingCampaign_thenOkWithSupportedFieldUpdated() throws Exception { // @formatter:off
        String updatedCampaignBody = baseCampaignInput()
            .with("tasks", Arrays.asList(taskResource))
            .with("code", "UPDATED-CODE")
            .with("name", "Updated Name")
            .with("description", "Updated Description")
            .build();

        
        webClient.put("/campaigns/2", updatedCampaignBody)
            .fieldsMatch(
                entry("id", equalTo(2)),
                entry("code", not(equalTo("UPDATED-CODE"))),
                entry("name", equalTo("Updated Name")),
                entry("description", equalTo("Updated Description")),
                entry("tasks", not(empty())),
                entry("tasks..name", not(hasItem("Test - Template Task Name"))));
     
    } // @formatter:on

    @Test
    void givenPreloadedData_whenUpdateNonExistingCampaign_thenNotFound() throws Exception {
        String updatedCampaignBody = generateCampaignInput();

        webClient.requestWithResponseStatus("/campaigns/99", HttpMethod.PUT, updatedCampaignBody, HttpStatus.NOT_FOUND);
    }

    // PUT - update - validations

    @Test
    void givenPreloadedData_whenUpdateWithInvalidFields_thenBadRequest() throws Exception {
        // null name
        String nullNameCampaignBody = baseCampaignInput().withNull("name")
            .build();

        webClient.requestWithResponseStatus("/campaigns/2", HttpMethod.PUT, nullNameCampaignBody, HttpStatus.BAD_REQUEST);
    }

    private String generateCampaignInput() throws IOException {
        return fromResource(this.resource).withRandom("code")
            .build();
    }

    private SimpleRequestBodyBuilder baseCampaignInput() throws IOException {
        return fromResource(this.resource).withRandom("code");
    }
}
