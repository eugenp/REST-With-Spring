package com.baeldung.rws.endtoend.simplified;

import static com.baeldung.rws.commons.endtoend.spec.DtoFieldSpec.from;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;

import com.baeldung.rws.domain.model.TaskStatus;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.rws.commons.endtoend.client.SimpleWebTestClient;
import com.baeldung.rws.commons.endtoend.spec.DtoFieldSpec;
import com.baeldung.rws.endtoend.simplified.utils.CampaignDtoSpec;
import com.baeldung.rws.web.dto.CampaignDto;
import com.baeldung.rws.web.dto.TaskDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampaignsSimplifiedEndToEndApiTest {

    @Autowired
    SimpleWebTestClient webClient;

    // GET - single - status

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenExpectOkStatus() {
        webClient.get("/campaigns/3", CampaignDto.class);
    }

    // GET - single - FieldSpec

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseBodyContainsExpectedValuesCheckingWithFieldSpecList() {
        DtoFieldSpec<CampaignDto, Long> id = from("id", CampaignDto::id);
        id.define(equalTo(3L));

        DtoFieldSpec<CampaignDto, String> code = DtoFieldSpec.from("code", CampaignDto::code);
        code.define(containsString("C3"));

        webClient.getForFieldSpec("/campaigns/3", CampaignDto.class)
            .valueMatches(id, code);
    }

    // GET - single - 1

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseBodyContainsExpectedValues() { // @formatter:off
        webClient.get("/campaigns/3", CampaignDto.class)
            .valueMatches(new CampaignDtoSpec(
                equalTo(3L),
                containsString("C3"),
                anyOf(any(String.class), nullValue()),
                not(blankOrNullString()),
                collectionOfType(TaskDto.class)));
    } // @formatter:on

    // GET - single - 2

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseFieldsMatch() { // @formatter:off
        webClient.get("/campaigns/3", CampaignDto.class)
            .valueMatches(new CampaignDtoSpec(
                equalTo(3L),
                not(containsString(" ")),
                containsString("Campaign"),
                not(blankOrNullString()),
                collectionOfType(TaskDto.class)));
    } // @formatter:on

    // GET - list

    @Test
    void givenPreloadedData_whenGetCampaigns_thenResponseFieldsMatch() {
        CampaignDtoSpec expected1 = new CampaignDtoSpec(equalTo(1L), any(String.class), any(String.class), any(String.class), collectionOfType(TaskDto.class));
        CampaignDtoSpec expected2 = new CampaignDtoSpec(any(Long.class), equalTo("C2"), any(String.class), any(String.class), collectionOfType(TaskDto.class));

        webClient.getList("/campaigns", CampaignDto.class)
            .contains(expected1, expected2)
            .value(campaignList -> {
                assertThat(campaignList).hasSizeGreaterThanOrEqualTo(3);
            });
    }

    // POST - create

    @Test
    void whenCreateNewCampaign_thenCreatedAndResponseFieldsMatch() {
        CampaignDto newCampaignBody = new CampaignDto(null, "TEST-E2E-S-CAMPAIGN-NEW-4", "Test - New Campaign 4", "Description of new test campaign 4", emptySet());

        webClient.create("/campaigns", newCampaignBody)
                .valueMatches(new CampaignDtoSpec(greaterThan(3L),
                        equalTo("TEST-E2E-S-CAMPAIGN-NEW-4"),
                        equalTo("Test - New Campaign 4"),
                        equalTo("Description of new test campaign 4"),
                        emptyIterable()));
    }

    // POST - new - validations

    @Test
    void whenCreateNewCampaignWithoutRequiredCodeField_thenBadRequest() {
        // null code
        CampaignDto nullCodeCampaignBody = new CampaignDto(null, null, "Test - New Campaign Invalid", "Description of new test campaign Invalid", null);

        webClient.requestWithResponseStatus("/campaigns", HttpMethod.POST, nullCodeCampaignBody, HttpStatus.BAD_REQUEST);
    }

    // PUT - update

    @Test
    void givenPreloadedData_whenUpdateExistingCampaign_thenOkWithSupportedFieldUpdated() {
        TaskDto taskBody = new TaskDto(null, null, "Test - Task X13", "Description of task", LocalDate.of(2030, 01, 01), TaskStatus.DONE, null, null);
        Set<TaskDto> tasksListBody = Set.of(taskBody);
        CampaignDto updatedCampaignBody = new CampaignDto(null, "TEST-E2E-S-CAMPAIGN-UPDATED-1", "Test - Updated Campaign 2", "Description of updated test campaign 2", tasksListBody);

        webClient.put("/campaigns/2", updatedCampaignBody)
            .valueMatches(new CampaignDtoSpec(equalTo(2L), not(equalTo(updatedCampaignBody.code())), equalTo(updatedCampaignBody.name()), equalTo(updatedCampaignBody.description()), allOf(not(emptyIterable())
            // , not(contains(hasProperty("name", taskBody.name()))) // hasProperty doesn't work for Java Records
            )))
            .value(resultCampaign -> assertThat(resultCampaign.tasks()).noneMatch(task -> task.name()
                .equals(taskBody.name())));
    }

    // PUT - update - validations

    @Test
    void givenPreloadedData_whenUpdateWithInvalidFields_thenBadRequest() {
        // null name
        CampaignDto nullNameCampaignBody = new CampaignDto(null, "TEST-E2E-S-CAMPAIGN-UPDATED-3", null, "Description of updated test campaign 3", null);

        webClient.requestWithResponseStatus("/campaigns/2", HttpMethod.PUT, nullNameCampaignBody, HttpStatus.BAD_REQUEST);
    }

    private static <U> Matcher<Iterable<? extends U>> collectionOfType(Class<U> clazz) {
        return everyItem(any(clazz));
    }
}
