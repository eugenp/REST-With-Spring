package org.rest.sec.web.controller;

import org.rest.sec.web.common.UriMappingConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class RedirectController {

    public RedirectController() {
        super();
    }

    // API

    @RequestMapping(value = "privilege")
    public ResponseEntity<Void> privilegesToPrivilege() {
        final HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, UriMappingConstants.PRIVILEGES);

        final ResponseEntity<Void> redirectResponse = new ResponseEntity<Void>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);

        return redirectResponse;
    }

    @RequestMapping(value = "role")
    public ResponseEntity<Void> rolesToRole() {
        final HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, UriMappingConstants.ROLES);

        final ResponseEntity<Void> redirectResponse = new ResponseEntity<Void>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);

        return redirectResponse;
    }

    @RequestMapping(value = "user")
    public ResponseEntity<Void> usersToUser() {
        final HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, UriMappingConstants.USERS);

        final ResponseEntity<Void> redirectResponse = new ResponseEntity<Void>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);

        return redirectResponse;
    }

}
