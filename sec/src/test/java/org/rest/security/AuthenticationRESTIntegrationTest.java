package org.rest.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.dto.User;
import org.rest.sec.util.SecurityConstants;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.context.ContextTestConfig;
import org.rest.testing.security.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AuthenticationRESTIntegrationTest {

    @Autowired private SecBusinessPaths paths;

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
        return AuthenticationUtil.givenBasicAuthenticated();
    }

}
