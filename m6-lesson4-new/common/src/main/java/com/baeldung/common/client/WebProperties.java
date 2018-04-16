package com.baeldung.common.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public final class WebProperties {

    @Value("${http.sec.auth.server.path}")
    private String authServerPath;
    
    @Value("${http.sec.resource.server.path}")
    private String resourceServerPath;

    @Value("${http.oauthPath}")
    private String oauthPath;

    @Autowired
    private CommonPaths commonPaths;

    public WebProperties() {
        super();
    }

    // API
   

    public final String getOauthPath() {
        return oauthPath;
    }

    public String getAuthServerPath() {
        return authServerPath;
    }

    public String getResourceServerPath() {
        return resourceServerPath;
    }

    public final String getProtocol() {
        return commonPaths.getProtocol();
    }

    public final String getHost() {
        return commonPaths.getHost();
    }

    public final int getAuthServerPort() {
        return commonPaths.getAuthServerPort();
    }
    
    public final int getresourceServerPort() {
        return commonPaths.getResourceServerPort();
    }

}
