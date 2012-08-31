package org.rest.common.client;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.template.IClientTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.util.IDUtils;
import org.rest.common.util.QueryConstants;
import org.rest.common.util.SearchField;
import org.rest.common.web.WebConstants;
import org.springframework.web.client.RestClientException;

public abstract class AbstractClientLogicRESTIntegrationTest<T extends INameableEntity> {

    public AbstractClientLogicRESTIntegrationTest() {
        super();
    }

    // tests

    // find - one

    @Test(expected = RestClientException.class)
    public void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenExceptionIsThrown() {
        getAPI().findOneByURI(getURI() + WebConstants.PATH_SEP + randomNumeric(4));
    }

    @Test
    public final void givenResourceExists_whenResourceIsRetrieved_thenResourceHasId() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getAPI().createAsURI(newResource);

        // When
        final T createdResource = getAPI().findOneByURI(uriOfExistingResource);

        // Then
        assertThat(createdResource.getId(), notNullValue());
    }

    @Test
    public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getAPI().createAsURI(newResource);

        // When
        final T createdResource = getAPI().findOneByURI(uriOfExistingResource);

        // Then
        assertEquals(createdResource, newResource);
    }

    @Test
    /**/public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
        // When
        final T createdResource = getAPI().findOne(IDUtils.randomPositiveLong());

        // Then
        assertNull(createdResource);
    }

    // find one - by name

    /**
     * note: - the rest template encodes the URI wrong (q=key=val is seen as q=key - one param and val=null - another param)
     * see: http://forum.springsource.org/showthread.php?129138-Possible-bug-in-RestTemplate-double-checking-before-opening-a-JIRA&p=421494#post421494
     */
    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByName_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().findByName(existingResource.getName());
    }

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByName_thenResourceIsFound() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final T resourceByName = getAPI().findByName(existingResource.getName());

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByName_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final T resourceByName = getAPI().findByName(existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    // find one - by attributes

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().findOneByAttributes(SearchField.name.name(), existingResource.getName());
    }

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenResourceIsFound() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByAttributes(SearchField.name.name(), existingResource.getName());

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByAttributes(SearchField.name.name(), existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNagatedNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().findAllByAttributes(QueryConstants.NAME_NEG, existingResource.getName());

        // Then
    }

    // find - all

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenNoExceptions() {
        getAPI().findAll();
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenTheResultIsNotNull() {
        final List<T> resources = getAPI().findAll();

        assertNotNull(resources);
    }

    @Test
    /**/public void givenAnResourceExists_whenAllResourcesAreRetrieved_thenTheExistingResourceIsIndeedAmongThem() {
        final T existingResource = getAPI().create(createNewEntity());

        final List<T> resources = getAPI().findAll();

        assertThat(resources, hasItem(existingResource));
    }

    @Test
    public void whenAllResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() {
        // Given
        getAPI().createAsURI(createNewEntity());

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        assertThat(allResources, not(Matchers.<T> empty()));
    }

    @Test
    public void whenAllResourcesAreRetrieved_thenResourcesHaveIds() {
        // Given
        getAPI().createAsURI(createNewEntity());

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        for (final T resource : allResources) {
            assertNotNull(resource.getId());
        }
    }

    // find - all (+sorting + pagination)

    // create

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsCreated_thenException() {
        getAPI().create(null);
    }

    @Test
    /**/public void whenResourceIsCreated_thenNoExceptions() {
        getAPI().createAsURI(createNewEntity());
    }

    @Test
    /**/public void whenResourceIsCreated_thenResourceIsRetrievable() {
        final T existingResource = getAPI().create(createNewEntity());

        assertNotNull(getAPI().findOne(existingResource.getId()));
    }

    @Test
    /**/public void whenResourceIsCreated_thenSavedResourceIsEqualToOriginalResource() {
        final T originalResource = createNewEntity();
        final T savedResource = getAPI().create(originalResource);

        assertEquals(originalResource, savedResource);
    }

    // update

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsUpdated_thenException() {
        getAPI().update(null);
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().update(existingResource);
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getEntityOps().change(existingResource);
        getAPI().update(existingResource);

        final T updatedResourceFromServer = getAPI().findOne(existingResource.getId());

        // Then
        assertEquals(existingResource, updatedResourceFromServer);
    }

    // delete

    @Test
    public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().delete(existingResource.getId());

        // Then
        assertNull(getAPI().findOne(existingResource.getId()));
    }

    // template method

    protected abstract IClientTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected final String getURI() {
        return getAPI().getURI() + WebConstants.PATH_SEP;
    }

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

}
