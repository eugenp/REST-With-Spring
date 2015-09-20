package org.baeldung.um.client;

import org.baeldung.common.client.CommonPaths;
import org.baeldung.common.web.IUriMapper;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public final class UmPaths {

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
        return getRootUri() + uriMapper.getUriBase(UserDto.class);
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
