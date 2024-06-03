package com.baeldung.rwsb.commons.endtoend.client;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@Lazy
@Component
public class SimpleWebTestClient {

    WebTestClient webClient;

    public SimpleWebTestClient(WebTestClient webClient) {
        super();
        this.webClient = webClient;
    }

    public void get(String url) {
        webClient.get()
            .uri(url)
            .exchange()
            .expectStatus()
            .isOk();
    }
    
    public <T> FieldSpecBodySpec<T> getForFieldSpec(String url, Class<T> clazz) {
        return new FieldSpecBodySpec<T>(webClient.get()
            .uri(url)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(clazz));
    }

    public <T> SimpleBodySpec<T> get(String url, Class<T> clazz) {
        return new SimpleBodySpec<T>(webClient.get()
            .uri(url)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(clazz));
    }

    public <T> SimpleListBodySpec<T> getList(String url, Class<T> clazz) {
        return new SimpleListBodySpec<T>(webClient.get()
            .uri(url)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(clazz));
    }

    @SuppressWarnings("unchecked")
    public <T> SimpleBodySpec<T> create(String url, T body) {
        return new SimpleBodySpec<T>(webClient.post()
            .uri(url)
            .body(Mono.just(body), body.getClass())
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody((Class<T>) body.getClass()));
    }

    @SuppressWarnings("unchecked")
    public <T> SimpleBodySpec<T> put(String url, T body) {
        return new SimpleBodySpec<T>(webClient.put()
            .uri(url)
            .body(Mono.just(body), body.getClass())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody((Class<T>) body.getClass()));
    }

    public void requestWithResponseStatus(String url, HttpMethod method, Object body, HttpStatus responseStatus) {
        webClient.method(method)
            .uri(url)
            .body(Mono.just(body), body.getClass())
            .exchange()
            .expectStatus()
            .isEqualTo(responseStatus);
    }
}
