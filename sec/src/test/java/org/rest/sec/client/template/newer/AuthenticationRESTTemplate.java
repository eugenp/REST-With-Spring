package org.rest.sec.client.template.newer;

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
public class AuthenticationRESTTemplate {

    @Autowired
    IMarshaller marshaller;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SecBusinessPaths paths;

    public AuthenticationRESTTemplate() {
        super();
    }

    // API

    public final ResponseEntity<User> authenticate(final String username, final String password) {
        return restTemplate.exchange(getURI(), HttpMethod.POST, new HttpEntity<User>(createHeaders(username, password)), User.class);
    }

    // util

    final String getURI() {
        return paths.getAuthenticationUri();
    }

    final HttpHeaders createHeaders(final String username, final String password) {
        return HeaderUtil.createAcceptAndBasicAuthHeaders(marshaller, username, password);
    }

}
