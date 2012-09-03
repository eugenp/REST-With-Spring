package org.rest.sec.client.template.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.sec.client.template.newer.AuthenticationRESTTemplate;
import org.rest.sec.model.dto.User;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextConfig;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AuthenticationClientRESTIntegrationTest {

    @Autowired
    private AuthenticationRESTTemplate authenticationRestTemplate;

    // tests

    // GET

    @Test
    public final void whenAuthenticating_then201IsReceived() {
        // When
        final ResponseEntity<User> response = authenticationRestTemplate.authenticate(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD);

        // Then
        assertThat(response.getStatusCode().value(), is(201));
    }

}
