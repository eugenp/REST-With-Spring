package com.baeldung.common.client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
@Profile("client")
public final class CommonPaths implements InitializingBean {

    @Autowired
    private Environment env;

    @Value("${http.protocol}")
    private String protocol;

    @Value("${http.host}")
    private String host;

    @Value("${http.auth.server.port}")
    private int authServerPort;

    @Value("${http.resource.server.port}")
    private int resourceServerPort;

    public CommonPaths() {
        super();
    }

    // API

    public final String getAuthServerRoot() {
        if (authServerPort == 80) {
            return protocol + "://" + host;
        }
        return protocol + "://" + host + ":" + authServerPort;
    }

    public final String getResourceServerRoot() {
        if (resourceServerPort == 80) {
            return protocol + "://" + host;
        }
        return protocol + "://" + host + ":" + resourceServerPort;
    }

    public final String getProtocol() {
        return protocol;
    }

    public final String getHost() {
        return host;
    }

    public int getAuthServerPort() {
        return authServerPort;
    }

    public int getResourceServerPort() {
        return resourceServerPort;
    }

    @Override
    public void afterPropertiesSet() {
        if (protocol == null || protocol.equals("${http.protocol}")) {
            protocol = Preconditions.checkNotNull(env.getProperty("http.protocol"));
        }
        if (host == null || host.equals("${http.host}")) {
            host = Preconditions.checkNotNull(env.getProperty("http.host"));
        }
        authServerPort = Preconditions.checkNotNull(env.getProperty("http.auth.server.port", Integer.class));
        resourceServerPort = Preconditions.checkNotNull(env.getProperty("http.resource.server.port", Integer.class));
    }

}
