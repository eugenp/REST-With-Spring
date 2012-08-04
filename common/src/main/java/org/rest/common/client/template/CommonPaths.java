package org.rest.common.client.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public final class CommonPaths {

    @Value("${http.protocol}")
    private String protocol;
    @Value("${http.host}")
    private String host;
    @Value("${http.port}")
    private String port;

    // API

    public final String getServerRoot() {
        return protocol + "://" + host + ":" + port;
    }

}
