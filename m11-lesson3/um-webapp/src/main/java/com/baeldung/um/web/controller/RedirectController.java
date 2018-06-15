package com.baeldung.um.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.um.util.UmMappings;

@RestController
public class RedirectController {

    @GetMapping(UmMappings.Singular.PRIVILEGE)
    public ResponseEntity<Void> privilegeToPrivileges(ServerHttpRequest request) {
        return singularToPlural(request);
    }

    @GetMapping(UmMappings.Singular.ROLE)
    public ResponseEntity<Void> roleToRoles(ServerHttpRequest request) {
        return singularToPlural(request);
    }

    @GetMapping(UmMappings.Singular.USER)
    public ResponseEntity<Void> userToUsers(ServerHttpRequest request) {
        return singularToPlural(request);
    }

    // util

    private ResponseEntity<Void> singularToPlural(ServerHttpRequest request) {
        String correctUri = request.getPath().contextPath().value() + request.getPath().pathWithinApplication().value() + "s";
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, correctUri);

        return new ResponseEntity<>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
