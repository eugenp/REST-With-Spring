package com.baeldung.rwsb.web.error;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

public class IdMismatchException extends ErrorResponseException {

    private static final long serialVersionUID = 8022209811924828517L;

    public IdMismatchException(String message) {
        super(HttpStatus.BAD_REQUEST);
        super.setType(URI.create("https://example.com/errors/id-mismatch-exception"));
        super.setTitle("Path param and body ids didn't match");
        super.setDetail(message);
    }
}