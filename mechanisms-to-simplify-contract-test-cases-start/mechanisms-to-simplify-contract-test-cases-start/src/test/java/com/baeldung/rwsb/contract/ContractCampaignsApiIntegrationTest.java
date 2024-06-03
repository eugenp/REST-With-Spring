package com.baeldung.rwsb.contract;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractCampaignsApiIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Value("classpath:campaign-template.json")
    Resource templateResource;

    @Test
    void givenPreloadedData_whenGetCampaigns_thenResponseFieldsMatch() {
        webClient.get()
            .uri("/campaigns")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$[?(@.code == 'C1')].tasks.length()")
            .value(everyItem(greaterThanOrEqualTo(3)))
            .jsonPath("$[?(@.code == 'C1')].tasks[?(@.name == 'Task 2')].description")
            .isEqualTo("Task 2 Description")
            .jsonPath("$..tasks..name")
            .value(hasItems("Task 1", "Task 2", "Task 3", "Task 4"));
    }

    @Test
    void whenCreateNewCampaignFromJsonPatternFile_thenCreatedAndResponseFieldsMatch() throws Exception {
        String newCampaignJsonBody = generateCampaignJsonFromTemplateFile("TEST-C-CAMPAIGN-NEW-JSON-3", "Test - New JSON Campaign 3", null);

        webClient.post()
            .uri("/campaigns")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newCampaignJsonBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(3))
            .jsonPath("$.code")
            .isEqualTo("TEST-C-CAMPAIGN-NEW-JSON-3")
            .jsonPath("$.name")
            .isEqualTo("Test - New JSON Campaign 3")
            .jsonPath("$.description")
            .isEqualTo("Template description")
            .jsonPath("$.tasks")
            .isEmpty();
    }

    private String generateCampaignJsonFromTemplateFile(String code, String name, String description) throws Exception {
        Reader reader = new InputStreamReader(templateResource.getInputStream(), StandardCharsets.UTF_8);
        String campaignTemplate = FileCopyUtils.copyToString(reader);
        JsonNode node = new ObjectMapper().readTree(campaignTemplate);
        ObjectNode objectNode = ((ObjectNode) node);
        objectNode.put("code", code);
        if (name != null)
            objectNode.put("name", name);
        if (description != null)
            objectNode.put("description", description);
        return objectNode.toString();
    }
}
