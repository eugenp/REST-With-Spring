package org.baeldung.rest.client;

import static org.baeldung.rest.common.spring.util.CommonSpringProfileUtil.CLIENT;
import static org.baeldung.rest.common.spring.util.CommonSpringProfileUtil.TEST;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.baeldung.rest.client.auth.AuthenticationRestTemplate;
import org.baeldung.rest.model.User;
import org.baeldung.rest.spring.ClientTestConfig;
import org.baeldung.rest.spring.ContextConfig;
import org.baeldung.rest.spring.SecCommonApiConfig;
import org.baeldung.rest.util.SecurityConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles({ CLIENT, TEST })
public class AuthenticationClientRestLiveTest {

    @Autowired
    private AuthenticationRestTemplate authenticationRestTemplate;

    // tests

    // GET

    @Test
    public final void whenAuthenticating_then200IsReceived() {
        // When
        final ResponseEntity<User> response = authenticationRestTemplate.authenticate(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS);

        // Then
        assertThat(response.getStatusCode().value(), is(200));
    }

}
