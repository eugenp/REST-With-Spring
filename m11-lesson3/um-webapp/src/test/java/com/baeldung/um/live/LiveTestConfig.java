package com.baeldung.um.live;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LiveTestConfig {

    private final static String BASE_URI = "http://localhost:8082/api";

    @Bean
    WebClient webClientBuilder() {

        // @formatter:off
        return WebClient
                .builder()
                     .baseUrl(BASE_URI)                  
                     .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        // @formatter:on

    }

    @Bean
    WebTestClient createWebTestclient() {
        return WebTestClient.bindToServer().baseUrl(BASE_URI).build();
    }
}
