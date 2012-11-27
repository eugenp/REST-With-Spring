package org.rest.sec.web.controller;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = UriMappingConstants.Singural.PRIVILEGE)
    public ResponseEntity<Void> privilegesToPrivilege(final HttpServletRequest request) {
        final String correctUri = request.getRequestURL().toString() + "s";
        final HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, correctUri);

        final ResponseEntity<Void> redirectResponse = new ResponseEntity<Void>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);

        return redirectResponse;
    }

    @RequestMapping(value = UriMappingConstants.Singural.ROLE)
    public ResponseEntity<Void> rolesToRole(final HttpServletRequest request) {
        final String correctUri = request.getRequestURL().toString() + "s";
        final HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, correctUri);

        final ResponseEntity<Void> redirectResponse = new ResponseEntity<Void>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);

        return redirectResponse;
    }

    @RequestMapping(value = UriMappingConstants.Singural.USER)
    public ResponseEntity<Void> usersToUser(final HttpServletRequest request) {
        final String correctUri = request.getRequestURL().toString() + "s";
        final HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(org.apache.http.HttpHeaders.LOCATION, correctUri);

        final ResponseEntity<Void> redirectResponse = new ResponseEntity<Void>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);

        return redirectResponse;
    }

}
