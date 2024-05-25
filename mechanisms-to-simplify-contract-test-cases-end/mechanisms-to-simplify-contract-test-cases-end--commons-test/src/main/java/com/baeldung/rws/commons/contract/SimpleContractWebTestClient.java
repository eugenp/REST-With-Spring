package com.baeldung.rws.commons.contract;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

@Lazy
@Component
public class SimpleContractWebTestClient {

    WebTestClient webClient;

    public SimpleContractWebTestClient(WebTestClient webClient) {
        super();
        this.webClient = webClient;
    }

    // FIRST BASE APPROACH
    /* public void create(String url, String jsonBody) {
         webClient.post()
             .uri(url)
             .contentType(MediaType.APPLICATION_JSON)
             .bodyValue(jsonBody)
             .exchange()
             .expectStatus()
             .isCreated();
    }*/

    public SimpleContractBodyContentSpec create(String url, String jsonBody) {
        return new SimpleContractBodyContentSpec(webClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(jsonBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody());
    }

    public SimpleContractBodyContentSpec requestWithResponseStatus(String url, HttpMethod method, String jsonBody, HttpStatus responseStatus) {
        return new SimpleContractBodyContentSpec(webClient.method(method)
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(jsonBody)
            .exchange()
            .expectStatus()
            .isEqualTo(responseStatus)
            .expectBody());
    }

    public SimpleContractBodyContentSpec get(String url) {
        return new SimpleContractBodyContentSpec(webClient.get()
            .uri(url)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody());
    }

    public SimpleContractBodyContentSpec put(String url, String jsonBody) {
        return new SimpleContractBodyContentSpec(webClient.put()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(jsonBody)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody());
    }

    public <T> T extractResponseBody(String url, HttpMethod method, Class<T> clazz, String jsonBody) {
        return webClient.method(method)
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(jsonBody)
            .exchange()
            .returnResult(clazz)
            .getResponseBody()
            .blockFirst();
    }
}
