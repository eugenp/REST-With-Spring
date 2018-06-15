package com.baeldung.um.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.um.util.UmMappings;

@RestController
public class RedirectController {

    @GetMapping(UmMappings.Singular.PRIVILEGE)
    public ResponseEntity<Void> privilegeToPrivileges(HttpServletRequest request) {
        return singularToPlural(request);
    }

    @GetMapping(UmMappings.Singular.ROLE)
    public ResponseEntity<Void> roleToRoles(HttpServletRequest request) {
        return singularToPlural(request);
    }

    @GetMapping(UmMappings.Singular.USER)
    public ResponseEntity<Void> userToUsers(HttpServletRequest request) {
        return singularToPlural(request);
    }

    // util

    private ResponseEntity<Void> singularToPlural(HttpServletRequest request) {
        String correctUri = request.getRequestURL().toString() + "s";
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, correctUri);

        return new ResponseEntity<>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
