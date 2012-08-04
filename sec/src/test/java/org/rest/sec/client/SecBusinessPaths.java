package org.rest.sec.client;

import org.rest.common.client.SecPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public final class SecBusinessPaths {

    @Autowired
    SecPaths secPaths;

    // API

    public final String getUserUri() {
        return secPaths.getRootUri() + "user";
    }

    public final String getPrivilegeUri() {
        return secPaths.getRootUri() + "privilege";
    }

    public final String getRoleUri() {
        return secPaths.getRootUri() + "role";
    }

    public final String getAuthenticationUri() {
        return secPaths.getRootUri() + "authentication";
    }

    public final String getLoginUri() {
        return secPaths.getContext() + "/j_spring_security_check";
    }

    public final String getRootUri() {
        return secPaths.getRootUri();
    }

}
