package com.baeldung.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveWebSocketServer {
    
    public static void main(String[] args) {        
        SpringApplication.run(ReactiveWebSocketServer.class, args);        
    }
}
