package com.baeldung.rws.web.error;

public record CustomErrorBody( // @formatter:off
    String errorMessage,
    
    String errorCode) { } // @formatter:on
