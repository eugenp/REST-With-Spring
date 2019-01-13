package com.baeldung.um.web.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.FixtureResourceFactory;
import com.baeldung.um.client.template.RoleRestClient;
import com.baeldung.um.client.template.UserRestClient;
import com.baeldung.um.model.RoleDtoOpsImpl;
import com.baeldung.um.model.UserDtoOpsImpl;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.service.AsyncService;
import com.baeldung.um.test.live.UmLogicRestLiveTest;
import com.baeldung.um.util.Um;
import com.baeldung.um.web.dto.UserDto;
import com.google.common.collect.Sets;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserLogicRestLiveTest extends UmLogicRestLiveTest<UserDto> {

    @Autowired
    private UserRestClient api;
    @Autowired
    private RoleRestClient associationApi;

    @Autowired
    private UserDtoOpsImpl entityOps;
    @Autowired
    private RoleDtoOpsImpl associationOps;

    public UserLogicRestLiveTest() {
        super(UserDto.class);
    }

    // tests

    // find - one

    @Test
    @Ignore("in progress - create association first")
    public final void whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved() {
        final UserDto existingResource = getApi().create(getEntityOps().createNewResource());
        assertThat(existingResource.getRoles(), not(Matchers.<Role> empty()));
    }

    // create

    /**
     * - note: this test ensures that a new User cannot automatically create new Privileges <br>
     * - note: the standard way to do this is: first create the Privilege resource(s), then associate them with the new User resource and then create the User resource
     */
    @Test
    public final void whenResourceIsCreatedWithNewAssociation_then409IsReceived() {
        final UserDto newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(getAssociationEntityOps().createNewResource());

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    @Ignore("intermitent failures - temporarily ignored")
    public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived() {
        final Role invalidAssociation = getAssociationEntityOps().createNewResource();
        invalidAssociation.setId(1001l);
        final UserDto newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(invalidAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenUserIsCreatedWithExistingRole_then201IsReceived() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final UserDto newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(existingAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    public void whenCreateUserAsyncUsingCallable_thenCreatedWithDelay() {
        Response response = createRandomUser().post(getApi().getUri() + "/callable");

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
        assertTrue(response.time() > AsyncService.DELAY);
    }

    @Test
    public void whenCreateUserAsyncUsingDeferredResult_thenCreatedWithDelay() {
        Response response = createRandomUser().post(getApi().getUri() + "/deferred");

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
        assertTrue(response.time() > AsyncService.DELAY);
    }

    @Test
    public void whenCreateUserAsync_thenAccepted() throws InterruptedException {
        Response response = createRandomUser().post(getApi().getUri() + "/async");

        assertEquals(202, response.getStatusCode());
        assertTrue(response.time() < AsyncService.DELAY);
        String loc = response.getHeader("Location");
        assertNotNull(loc);

        Response checkLocResponse = getApi().givenReadAuthenticated().get(loc);
        assertTrue(checkLocResponse.getStatusCode() == 200);
        assertTrue(checkLocResponse.asString().contains("In Progress"));

        Thread.sleep(AsyncService.DELAY);
        Response finalLocResponse = getApi().givenReadAuthenticated().get(loc);
        assertEquals(200, finalLocResponse.getStatusCode());
        assertTrue(finalLocResponse.asString().contains("Ready"));
    }

    // TODO: sort

    @Test
    public final void whenScenario_getResource_getAssociationsById() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final UserDto resourceToCreate = getEntityOps().createNewResource();
        resourceToCreate.getRoles().add(existingAssociation);

        // When
        final UserDto existingResource = getApi().create(resourceToCreate);
        for (final Role associationOfResourcePotential : existingResource.getRoles()) {
            final Role existingAssociationOfResource = getAssociationAPI().findOne(associationOfResourcePotential.getId());
            assertThat(existingAssociationOfResource, notNullValue());
        }
    }

    // scenarios

    @Test
    public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted() {
        final Role child = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final UserDto parent = FixtureResourceFactory.createNewUser();
        parent.setRoles(Sets.newHashSet(child));
        final UserDto parentWithChild = getApi().create(parent);
        assertThat(parentWithChild.getRoles(), hasItem(child));

        final UserDto parent2 = FixtureResourceFactory.createNewUser();
        parent2.setRoles(Sets.newHashSet(child));
        getApi().createAsResponse(parent2);

        final UserDto resource1ViewOfServerAfter = getApi().findOne(parentWithChild.getId());
        assertThat(resource1ViewOfServerAfter.getRoles(), hasItem(child));
    }

    // 

    private RequestSpecification createRandomUser() {
        return RestAssured.given().auth().basic(Um.ADMIN_EMAIL, Um.ADMIN_PASS).contentType(MediaType.APPLICATION_JSON_VALUE).body(getEntityOps().createNewResource());
    }

    // template method

    @Override
    protected final UserRestClient getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<UserDto> getEntityOps() {
        return entityOps;
    }

    final RoleRestClient getAssociationAPI() {
        return associationApi;
    }

    final IDtoOperations<Role> getAssociationEntityOps() {
        return associationOps;
    }

}
