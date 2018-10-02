package com.baeldung.spring.reactive.websocket.client;

import java.net.URI;
import java.time.Duration;

import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

public class ReactiveWebSocketClient {
    public static void main(String[] args) throws InterruptedException {
        
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
                URI.create("ws://localhost:8082/event-emitter"),
                session -> session.send(Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
                             .thenMany(session.receive()
                                 .doOnNext(msg ->System.out.println("Received.....: " + msg.getPayloadAsText())))
                             .then()
                       )
              .block(Duration.ofSeconds(300L));

    }

}
