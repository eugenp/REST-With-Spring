package org.baeldung.common.client;

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
    @Value("${http.port}")
    private String port;

    public CommonPaths() {
        super();
    }

    // API

    public final String getServerRoot() {
        if (port.equals("80")) {
            return protocol + "://" + host;
        }
        return protocol + "://" + host + ":" + port;
    }

    //

    @Override
    public void afterPropertiesSet() {
        if (protocol == null || protocol.equals("${http.protocol}")) {
            protocol = Preconditions.checkNotNull(env.getProperty("http.protocol"));
        }
        if (host == null || host.equals("${http.host}")) {
            host = Preconditions.checkNotNull(env.getProperty("http.host"));
        }
        if (port == null || port.equals("${http.port}")) {
            port = Preconditions.checkNotNull(env.getProperty("http.port"));
        }
    }

}
