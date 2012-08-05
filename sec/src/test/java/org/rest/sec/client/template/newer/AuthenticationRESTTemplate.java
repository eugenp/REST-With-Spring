package org.rest.sec.client.template.newer;

import org.rest.common.client.marshall.IMarshaller;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.dto.User;
import org.rest.testing.security.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("xStreamMarshaller")
    IMarshaller marshaller;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SecBusinessPaths paths;

    //

    public final ResponseEntity<User> authenticate(final String username, final String password) {
        return restTemplate.exchange(getURI(), HttpMethod.POST, new HttpEntity<User>(createHeaders(username, password)), User.class);
    }

    // util

    final HttpHeaders createHeaders(final String username, final String password) {
        return new HttpHeaders() {
            {
                set(com.google.common.net.HttpHeaders.ACCEPT, marshaller.getMime());

                final String basicAuthorizationHeader = AuthenticationUtil.createBasicAuthenticationAuthorizationHeader(username, password);
                set(com.google.common.net.HttpHeaders.AUTHORIZATION, basicAuthorizationHeader);
            }
        };
    }

    final String getURI() {
        return paths.getAuthenticationUri();
    }

}
