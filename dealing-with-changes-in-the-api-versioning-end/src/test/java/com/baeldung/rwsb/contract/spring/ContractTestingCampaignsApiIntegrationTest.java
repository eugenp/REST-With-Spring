package com.baeldung.rwsb.contract.spring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

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
public class ContractTestingCampaignsApiIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Value("classpath:campaign-2.json")
    Resource resource;

    @Value("classpath:campaign-template-3.json")
    Resource templateResource;

    // GET - single

    @Test
    void givenPreloadedData_whenGetSingleCampaign_thenResponseContainsFields() {
        webClient.get()
            .uri("/campaigns/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .value(equalTo(1L), Long.class)
            .jsonPath("$.name")
            .exists()
            .jsonPath("$.tasks..name")
            .value(hasItem("Task 1"));
    }

    // GET - list

    @Test
    void givenPreloadedData_whenGetListCampaigns_thenResponseContainsFields() {
        webClient.get()
            .uri("/campaigns")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$..code")
            .value(hasItems("C1", "C2"))
            .jsonPath("$.length()")
            .value(greaterThanOrEqualTo(3));
    }

    // GET - error response

    @Test
    void givenPreloadedData_whenGetNonExistingCampaignCampaign_then404ResponseWithErrorFields() {
        webClient.get()
            .uri("/campaigns/99")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody()
            .jsonPath("$.title")
            .exists();

    }

    // POST - create

    @Test
    void whenCreateNewCampaignFromJsonString_thenCreatedAndResponseFieldsMatch() {
        String newCampaignJsonBody = generateCampaignJson("TEST-C-CAMPAIGN-NEW-JSON-1");

        webClient.post()
            .uri("/campaigns")
            .contentType(MediaType.parseMediaType("application/vnd.rwsb.api.v2+json"))
            .bodyValue(newCampaignJsonBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(3))
            .jsonPath("$.code")
            .isEqualTo("TEST-C-CAMPAIGN-NEW-JSON-1")
            .jsonPath("$.name")
            .isEqualTo("Test - New JSON Campaign 1")
            .jsonPath("$.description")
            .isEqualTo("Description of new JSON test campaign 1")
            .jsonPath("$.tasks")
            .isEmpty();
    }

    @Test
    void whenCreateNewCampaignFromJsonFile_thenCreatedAndResponseFieldsMatch() throws Exception {
        String newCampaignJsonBody = generateStaticCampaignJsonFromFile();

        webClient.post()
            .uri("/campaigns")
            .contentType(MediaType.parseMediaType("application/vnd.rwsb.api.v2+json"))
            .bodyValue(newCampaignJsonBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody()
            .jsonPath("$.id")
            .value(greaterThan(3))
            .jsonPath("$.code")
            .isEqualTo("TEST-CAMPAIGN-NEW-STATIC-JSON-2")
            .jsonPath("$.name")
            .isEqualTo("Test - New Static JSON Campaign 2")
            .jsonPath("$.description")
            .isEqualTo("Description of new static JSON test campaign 2")
            .jsonPath("$.tasks")
            .isEmpty();
    }

    @Test
    void whenCreateNewCampaignFromJsonPatternFile_thenCreatedAndResponseFieldsMatch() throws Exception {
        String newCampaignJsonBody = generateCampaignJsonFromTemplateFile("TEST-C-CAMPAIGN-NEW-JSON-3", "Test - New JSON Campaign 3", null);

        webClient.post()
            .uri("/campaigns")
            .contentType(MediaType.parseMediaType("application/vnd.rwsb.api.v2+json"))
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

    private static String generateCampaignJson(String code) {
        return """
            {
              "code": "%s",
              "name": "Test - New JSON Campaign 1",
              "description": "Description of new JSON test campaign 1"
            }
            """.formatted(code);
    }

    private String generateStaticCampaignJsonFromFile() throws Exception {
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
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
