package org.baeldung.test.common.client;

import static org.baeldung.common.spring.util.Profiles.CLIENT;
import static org.baeldung.common.spring.util.Profiles.TEST;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.baeldung.client.IDtoOperations;
import org.baeldung.client.template.IRawClientTemplate;
import org.baeldung.common.interfaces.IDto;
import org.baeldung.common.web.WebConstants;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ CLIENT, TEST })
public abstract class AbstractRawLogicClientLiveTest<T extends IDto> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Environment env;

    public AbstractRawLogicClientLiveTest() {
        super();
    }

    // fixtures

    @Before
    public void before() {
        // logger.info("Active Profiles are: " + env.getActiveProfiles());
    }

    // tests

    // find - all

    @Test
    public void givenAtLeastOneResourceExists_whenAllResourcesAreRetrieved_thenRetrievedResourcesAreNotEmpty() {
        // Given
        ensureOneResourceExists();

        // When
        final List<T> allResources = getApi().findAll();

        // Then
        assertThat(allResources, not(Matchers.<T> empty()));
    }

    @Test
    public void givenAnResourceExists_whenAllResourcesAreRetrieved_thenTheExistingResourceIsIndeedAmongThem() {
        final T existingResource = getApi().create(createNewEntity());

        final List<T> resources = getApi().findAll();

        assertThat(resources, hasItem(existingResource));
    }

    @Test
    public void whenAllResourcesAreRetrieved_thenResourcesHaveIds() {
        ensureOneResourceExists();

        // When
        final List<T> allResources = getApi().findAll();

        // Then
        for (final T resource : allResources) {
            assertNotNull(resource.getId());
        }
    }

    // find - all - pagination

    @Test
    public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned() {
        getApi().createAsUri(createNewEntity());

        // When
        final List<T> allPaginated = getApi().findAllPaginated(0, 1);

        // Then
        assertFalse(allPaginated.isEmpty());
    }

    // create

    @Test(expected = RuntimeException.class)
    @Ignore
    public void whenNullResourceIsCreated_thenException() {
        getApi().create(null);
    }

    @Test
    public void whenResourceIsCreated_thenNoExceptions() {
        getApi().createAsUri(createNewEntity());
    }

    @Test
    public void whenResourceIsCreated_thenResourceIsRetrievable() {
        final T existingResource = getApi().create(createNewEntity());

        assertNotNull(getApi().findOne(existingResource.getId()));
    }

    @Test
    public void whenResourceIsCreated_thenSavedResourceIsEqualToOriginalResource() {
        final T originalResource = createNewEntity();
        final T savedResource = getApi().create(originalResource);

        assertEquals(originalResource, savedResource);
    }

    // update

    @Test(expected = RuntimeException.class)
    public void whenNullResourceIsUpdated_thenException() {
        getApi().update(null);
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getApi().update(existingResource);
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getEntityOps().change(existingResource);
        getApi().update(existingResource);

        final T updatedResourceFromServer = getApi().findOne(existingResource.getId());

        // Then
        assertEquals(existingResource, updatedResourceFromServer);
    }

    // delete

    @Test
    public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getApi().delete(existingResource.getId());

        // Then
        assertNull(getApi().findOne(existingResource.getId()));
    }

    // count

    @Test
    public final void givenAtLeastOneResourceExists_whenCountIsPerformed_thenCountIsNotZero() {
        ensureOneResourceExists();

        assertTrue(getApi().count() > 0);
    }

    // template method

    protected abstract IDtoOperations<T> getEntityOps();

    protected T createNewEntity() {
        return getEntityOps().createNewResource();
    }

    protected void ensureOneResourceExists() {
        getApi().createAsUri(createNewEntity());
    }

    protected abstract IRawClientTemplate<T> getApi();

    protected final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

}
