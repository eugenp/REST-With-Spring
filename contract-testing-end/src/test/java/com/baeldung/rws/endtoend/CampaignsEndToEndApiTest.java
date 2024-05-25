package com.baeldung.rws.endtoend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.rws.web.dto.CampaignDto;
import com.baeldung.rws.web.dto.TaskDto;

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
}
