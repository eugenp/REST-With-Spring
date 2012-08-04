package org.rest.common.client;

import org.rest.common.client.template.CommonPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public final class SecPaths {

    @Autowired CommonPaths commonPaths;

    @Value("${http.sec.path}") private String secPaths;

    // API

    public final String getContext() {
        return commonPaths.getServerRoot() + secPaths;
    }

    public final String getRootUri() {
        return getContext() + "/api/";
    }

}
