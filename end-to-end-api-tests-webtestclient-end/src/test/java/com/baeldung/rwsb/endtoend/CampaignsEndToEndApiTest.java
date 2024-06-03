package com.baeldung.rwsb.endtoend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CampaignsEndToEndApiTest {// @formatter:off

    // Live Test
    
//    private static final String BASE_URL = "http://localhost:8080";
//    
//    WebTestClient webClient = WebTestClient.bindToServer()
//        .baseUrl(BASE_URL)
//        .build();
//
//    @Test
//    void givenRunningService_whenGetSingleCampaign_thenExpectStatus() {
//        webClient.get()
//            .uri("/campaigns/3")
//            .exchange()
//            .expectStatus()
//            .isOk();
//    }
    
    
    // Manually setting up WebTestClient
    
//    private static final String BASE_URL = "http://localhost:";
//
//    @LocalServerPort
//    int port;
//
//    @Test
//    void givenRunningService_whenGetSingleCampaign_thenExpectStatus() {
//        WebTestClient webClient = WebTestClient.bindToServer()
//            .baseUrl(BASE_URL + port)
//            .build();
//        webClient.get()
//            .uri("/campaigns/3")
//            .exchange()
//            .expectStatus()
//            .isOk();
//    }
    
    // @formatter:on

    // Autowiring WebTestClient

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
}
