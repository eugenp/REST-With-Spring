package com.baeldung.rwsb.web.error;

public record CustomErrorBody( // @formatter:off
    String errorMessage,
    
    String errorCode) { } // @formatter:on
