package org.rest.sec.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.client.security.ITestAuthenticator;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.dto.User;
import org.rest.sec.spring.ClientTestConfig;
import org.rest.sec.spring.ContextConfig;
import org.rest.sec.spring.SecCommonApiConfig;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles({ "client", "mime_json" })
public class AuthenticationRestIntegrationTest {

    @Autowired
    private SecBusinessPaths paths;
    @Autowired
    private ITestAuthenticator auth;

    // tests

    @Test
    public final void whenAuthenticationIsCreated_then201IsReceived() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).post(paths.getAuthenticationUri());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    public final void whenAuthenticationIsCreated_thenResponseHasContent() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).post(paths.getAuthenticationUri());

        // Then
        assertThat(response.asString(), is(notNullValue()));
    }

    @Test
    public final void whenAuthenticationIsCreated_thenResponseIsPrincipal() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).post(paths.getAuthenticationUri());

        // Then
        response.as(User.class);
    }

    @Test
    public final void whenAuthenticationIsCreated_thenPrincipalResponseIsCorrect() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).post(paths.getAuthenticationUri());

        // Then
        assertEquals(new User(SecurityConstants.NAME, SecurityConstants.PASS, null), response.as(User.class));
    }

    // util

    protected RequestSpecification givenAuthenticated() {
        return auth.givenBasicAuthenticated(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASS);
    }

}
