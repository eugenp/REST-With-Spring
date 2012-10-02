package org.rest.sec.client;

import org.rest.common.client.template.CommonPaths;
import org.rest.common.web.IUriMapper;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.model.dto.User;
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

    @Autowired
    private IUriMapper uriMapper;

    // API

    public final String getContext() {
        return commonPaths.getServerRoot() + secPath;
    }

    public final String getRootUri() {
        return getContext() + "/api/";
    }

    public final String getUserUri() {
        return getRootUri() + uriMapper.getUriBase(User.class);
    }

    public final String getRoleUri() {
        return getRootUri() + uriMapper.getUriBase(Role.class);
    }

    public final String getPrivilegeUri() {
        return getRootUri() + uriMapper.getUriBase(Privilege.class);
    }

    public final String getAuthenticationUri() {
        return getRootUri() + "authentication";
    }

    public final String getLoginUri() {
        return getContext() + "/j_spring_security_check";
    }

}
