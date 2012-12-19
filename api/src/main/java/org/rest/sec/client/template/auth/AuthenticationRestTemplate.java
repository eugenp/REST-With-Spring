package org.rest.sec.client.template.auth;

import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.web.HeaderUtil;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("client")
public class AuthenticationRestTemplate {

    @Autowired
    IMarshaller marshaller;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SecBusinessPaths paths;

    public AuthenticationRestTemplate() {
        super();
    }

    // API

    public final ResponseEntity<User> authenticate(final String username, final String password) {
        return restTemplate.exchange(getUri(), HttpMethod.GET, new HttpEntity<User>(createHeaders(username, password)), User.class);
    }

    // util

    final String getUri() {
        return paths.getAuthenticationUri();
    }

    final HttpHeaders createHeaders(final String username, final String password) {
        return HeaderUtil.createAcceptAndBasicAuthHeaders(marshaller, username, password);
    }

}
