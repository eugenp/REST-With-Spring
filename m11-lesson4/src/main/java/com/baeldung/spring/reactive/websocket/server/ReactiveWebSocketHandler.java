package com.baeldung.spring.reactive.websocket.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {
   
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 500; ++i) {
            list.add("Message#" + i);
        }        
        System.out.println("Messages created....at: "+ new Date());
        Flux<String> longFlux =  Flux.fromStream(list.stream());   
                       
        // @formatter:off

       return webSocketSession.send(longFlux.onBackpressureDrop(message->System.out.println("dropped......: "+ message))
                                             .map(message -> {
                                                 System.out.println("Dispatched...: "+message);
                                                 return webSocketSession.textMessage(message);
                                                 }))
                                             .and(webSocketSession.receive()
                                             .map(WebSocketMessage::getPayloadAsText));  
        
      
       // @formatter:on
    }
}
