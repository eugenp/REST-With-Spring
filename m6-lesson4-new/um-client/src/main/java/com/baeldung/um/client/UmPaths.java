package com.baeldung.um.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.baeldung.common.client.CommonPaths;
import com.baeldung.common.web.IUriMapper;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.persistence.model.User;

@Component
@Profile("client")
public final class UmPaths {

    @Value("${http.sec.auth.server.path}")
    private String secAuthServerPath;
    
    @Value("${http.sec.resource.server.path}")
    private String secResourceServerPath;

    @Value("${http.oauthPath}")
    private String oauthPath;

    @Autowired
    private CommonPaths commonPaths;

    @Autowired
    private IUriMapper uriMapper;

    // API

    public final String getResourceServerContext() {
        return commonPaths.getResourceServerRoot() + secResourceServerPath;
    }
    
    public final String getAuthServerContext() {
        return commonPaths.getResourceServerRoot() + secAuthServerPath;
    }

    public final String getRootUri() {
        return getResourceServerContext() + "/api/";
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
        return getAuthServerContext() + "/j_spring_security_check";
    }   

    public String getSecAuthServerPath() {
        return secAuthServerPath;
    }

    public String getSecResourceServerPath() {
        return secResourceServerPath;
    }

    public final String getOauthPath() {
        return oauthPath;
    }

}
