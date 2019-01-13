package com.baeldung.um.security;

import static com.baeldung.common.spring.util.Profiles.CLIENT;
import static com.baeldung.common.spring.util.Profiles.TEST;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.test.common.client.security.ITestAuthenticator;
import com.baeldung.um.client.template.UserTestRestTemplate;
import com.baeldung.um.model.UserDtoOpsImpl;
import com.baeldung.um.spring.CommonTestConfig;
import com.baeldung.um.spring.UmClientConfig;
import com.baeldung.um.spring.UmLiveTestConfig;
import com.baeldung.um.util.Um;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SecurityRestLiveTest {

    @Autowired
    private UserTestRestTemplate userTemplate;

    @Autowired
    private UserDtoOpsImpl userOps;

    @Autowired
    private ITestAuthenticator auth;

    // tests

    // Unauthenticated

    @Test
    public final void givenUnauthenticated_whenAResourceIsDeleted_then401IsReceived() {
        // Given
        final String uriOfExistingResource = userTemplate.createAsUri(userOps.createNewResource());

        // When
        final Response response = given().delete(uriOfExistingResource);

        // Then
        assertThat(response.getStatusCode(), is(401));
    }

    // Authenticated

    @Test
    public final void givenAuthenticatedByBasicAuth_whenResourceIsCreated_then201IsReceived() {
        // Given
        // When
        final Response response = givenAuthenticated().contentType(userTemplate.getMarshaller().getMime()).body(userOps.createNewResource()).post(userTemplate.getUri());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    // @Ignore("rest-assured 1.6.2 depends on Jackson 1.x; the new 1.6.3 depends on httpcore and httpclient 4.2.x (which is problematic with Spring)")
    public final void givenAuthenticatedByDigestAuth_whenResourceIsCreated_then201IsReceived() {
        // Given
        // When
        final Response response = givenAuthenticated().contentType(userTemplate.getMarshaller().getMime()).body(userOps.createNewResource()).post(userTemplate.getUri());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    // util

    protected final RequestSpecification givenAuthenticated() {
        return auth.givenAuthenticated(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }

}