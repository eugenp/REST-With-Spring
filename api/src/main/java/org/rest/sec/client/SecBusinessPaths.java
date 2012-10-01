package org.rest.sec.client;

import org.rest.common.client.template.CommonPaths;
import org.rest.sec.web.common.UriMappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public final class SecBusinessPaths {

    @Value("${http.sec.path}")
    private String secPath;

    @Autowired
    private CommonPaths commonPaths;

    // API

    public final String getContext() {
        return commonPaths.getServerRoot() + secPath;
    }

    public final String getRootUri() {
        return getContext() + "/api/";
    }

    public final String getUserUri() {
        return getRootUri() + UriMappingConstants.USERS;
    }

    public final String getPrivilegeUri() {
        return getRootUri() + UriMappingConstants.PRIVILEGES;
    }

    public final String getRoleUri() {
        return getRootUri() + UriMappingConstants.ROLES;
    }

    public final String getAuthenticationUri() {
        return getRootUri() + "authentication";
    }

    public final String getLoginUri() {
        return getContext() + "/j_spring_security_check";
    }

}
