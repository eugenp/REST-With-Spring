package org.rest.common.persistence;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.IService;
import org.rest.common.util.IDUtils;
import org.rest.common.util.SearchField;
import org.rest.common.util.order.OrderById;
import org.rest.common.util.order.OrderByName;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;

public abstract class AbstractServicePersistenceIntegrationTest<T extends INameableEntity> {

    // tests

    // AbstractClientSortAndPaginationRESTIntegrationTest

    // find - all - pagination

    @Test
    /**/public final void whenResourcesAreRetrievedPaginated_thenNoExceptions() {
        getAPI().findAllPaginated(1, 1);
    }

    @Test
    /**/public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned() {
        persistNewEntity();

        // When
        final List<T> allPaginated = getAPI().findAllPaginated(0, 1);

        // Then
        assertFalse(allPaginated.isEmpty());
    }

    // find - all - sorting

    @Test
    /**/public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        persistNewEntity();
        persistNewEntity();

        // When
        final List<T> resourcesSorted = getAPI().findAllSorted(SearchField.name.name(), Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesSorted));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedDescById_thenNoExceptions() {
        getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedAscById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.ASC.name());

        assertTrue(new OrderById<T>().isOrdered(resourcesOrderedById));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedDescById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());

        assertTrue(new OrderById<T>().reverse().isOrdered(resourcesOrderedById));
    }

    // find - all - pagination and sorting

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenNoExceptions() {
        getAPI().findAllPaginatedAndSorted(0, 41, SearchField.name.name(), Sort.Direction.DESC.name());
    }

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenResourcesAreIndeedOrdered() {
        persistNewEntity();
        persistNewEntity();

        // When
        final List<T> resourcesPaginatedAndSorted = getAPI().findAllPaginatedAndSorted(0, 4, SearchField.name.name(), Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesPaginatedAndSorted));
    }

    // the ORIGINAL

    // find - one

    @Test
    /**/public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
        // When
        final T createdResource = getAPI().findOne(IDUtils.randomPositiveLong());

        // Then
        assertNull(createdResource);
    }

    @Test
    public void givenResourceExists_whenResourceIsRetrieved_thenNoExceptions() {
        final T existingResource = persistNewEntity();
        getAPI().findOne(existingResource.getId());
    }

    @Test
    public void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoExceptions() {
        getAPI().findOne(IDUtils.randomPositiveLong());
    }

    @Test
    public void givenResourceExists_whenResourceIsRetrieved_thenTheResultIsNotNull() {
        final T existingResource = persistNewEntity();
        final T retrievedResource = getAPI().findOne(existingResource.getId());
        assertNotNull(retrievedResource);
    }

    @Test
    public void givenResourceExists_whenResourceIsRetrieved_thenResourceIsRetrievedCorrectly() {
        final T existingResource = persistNewEntity();
        final T retrievedResource = getAPI().findOne(existingResource.getId());
        assertEquals(existingResource, retrievedResource);
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
    /**/public void whenAllResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() {
        persistNewEntity();

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        assertThat(allResources, not(Matchers.<T> empty()));
    }

    @Test
    /**/public void givenAnResourceExists_whenAllResourcesAreRetrieved_thenTheExistingResourceIsIndeedAmongThem() {
        final T existingResource = persistNewEntity();

        final List<T> resources = getAPI().findAll();

        assertThat(resources, hasItem(existingResource));
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenResourcesHaveIds() {
        persistNewEntity();

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        for (final T resource : allResources) {
            assertNotNull(resource.getId());
        }
    }

    // create

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsCreated_thenException() {
        getAPI().create(null);
    }

    @Test
    /**/public void whenResourceIsCreated_thenNoExceptions() {
        persistNewEntity();
    }

    @Test
    /**/public void whenResourceIsCreated_thenResourceIsRetrievable() {
        final T existingResource = persistNewEntity();

        assertNotNull(getAPI().findOne(existingResource.getId()));
    }

    @Test
    /**/public void whenResourceIsCreated_thenSavedResourceIsEqualToOriginalResource() {
        final T originalResource = createNewEntity();
        final T savedResource = getAPI().create(originalResource);

        assertEquals(originalResource, savedResource);
    }

    @Test(expected = DataAccessException.class)
    public void whenResourceWithFailedConstraintsIsCreated_thenException() {
        final T invalidResource = createNewEntity();
        getEntityOps().invalidate(invalidResource);

        getAPI().create(invalidResource);
    }

    /**
     * -- specific to the persistence engine
     */
    @Test(expected = DataAccessException.class)
    @Ignore("Hibernate simply ignores the id silently and still saved (tracking this)")
    public void whenResourceWithIdIsCreated_thenDataAccessException() {
        final T resourceWithId = createNewEntity();
        resourceWithId.setId(IDUtils.randomPositiveLong());

        getAPI().create(resourceWithId);
    }

    // update

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsUpdated_thenException() {
        getAPI().update(null);
    }

    @Test
    /**/public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
        // Given
        final T existingResource = persistNewEntity();

        // When
        getAPI().update(existingResource);
    }

    @Test(expected = DataAccessException.class)
    public void whenResourceIsUpdatedWithFailedConstraints_thenException() {
        final T existingResource = persistNewEntity();
        getEntityOps().invalidate(existingResource);

        getAPI().update(existingResource);
    }

    @Test
    /**/public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
        // Given
        final T existingResource = persistNewEntity();

        // When
        getEntityOps().change(existingResource);
        getAPI().update(existingResource);

        final T updatedResource = getAPI().findOne(existingResource.getId());

        // Then
        assertEquals(existingResource, updatedResource);
    }

    // delete

    @Test(expected = RuntimeException.class)
    public void givenResourceDoesNotExists_whenResourceIsDeleted_thenException() {
        // When
        getAPI().delete(IDUtils.randomPositiveLong());
    }

    @Test(expected = RuntimeException.class)
    public void whenResourceIsDeletedByNegativeId_thenException() {
        // When
        getAPI().delete(IDUtils.randomNegativeLong());
    }

    @Test
    public void givenResourceExists_whenEntityIsDeleted_thenNoExceptions() {
        // Given
        final T existingResource = persistNewEntity();

        // When
        getAPI().delete(existingResource.getId());
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
        // Given
        final T existingResource = persistNewEntity();

        // When
        getAPI().delete(existingResource.getId());

        // Then
        assertNull(getAPI().findOne(existingResource.getId()));
    }

    // template method

    protected abstract IService<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected abstract T createNewEntity();

    protected T persistNewEntity() {
        return getAPI().create(createNewEntity());
    }

}
