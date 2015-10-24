package org.baeldung.um.security;

import static com.jayway.restassured.RestAssured.given;
import static org.baeldung.common.spring.util.Profiles.CLIENT;
import static org.baeldung.common.spring.util.Profiles.TEST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.baeldung.test.common.client.security.ITestAuthenticator;
import org.baeldung.um.client.template.UserRestClient;
import org.baeldung.um.model.UserDtoOpsImpl;
import org.baeldung.um.spring.CommonTestConfig;
import org.baeldung.um.spring.UmClientConfig;
import org.baeldung.um.spring.UmContextConfig;
import org.baeldung.um.util.Um;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmContextConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SecurityRestLiveTest {

    @Autowired
    private UserRestClient userTemplate;

    @Autowired
    private UserDtoOpsImpl userOps;

    @Autowired
    private ITestAuthenticator auth;

    // tests

    // Unauthenticated

    @Test
    public final void givenUnauthenticated_whenAResourceIsDeleted_then401IsReceived() {
        // Given
        final String uriOfExistingResource = userTemplate.createAsUri(userOps.createNewResource(), null);

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
        return auth.givenBasicAuthenticated(Um.ADMIN_USERNAME, Um.ADMIN_PASS);
    }

}