package org.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class RESTPaths {

    @Value("${http.protocol}")
    private String protocol;
    @Value("${http.host}")
    private String host;
    @Value("${http.port}")
    private String port;
    @Value("${http.war}")
    private String war;

    // API

    public final String getServerRoot() {
	return protocol + "://" + host + ":" + port;
    }

    public final String getContext() {
	return protocol + "://" + host + ":" + port + war;
    }

}
