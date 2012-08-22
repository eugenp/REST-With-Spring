package org.rest.common.persistence;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
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
        getAPI().create(createNewEntity());

        // When
        final List<T> allPaginated = getAPI().findAllPaginated(0, 1);

        // Then
        assertFalse(allPaginated.isEmpty());
    }

    // find - all - sorting

    @Test
    /**/public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        getAPI().create(createNewEntity());
        getAPI().create(createNewEntity());

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
        getAPI().create(createNewEntity());
        getAPI().create(createNewEntity());

        // When
        final List<T> resourcesPaginatedAndSorted = getAPI().findAllPaginatedAndSorted(0, 4, SearchField.name.name(), Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesPaginatedAndSorted));
    }

    // AbstractClientLogicRESTIntegrationTest (TODO: continue including tests from there, here)

    // find - one

    @Test
    /**/public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
        // When
        final T createdResource = getAPI().findOne(IDUtils.randomPositiveLong());

        // Then
        assertNull(createdResource);
    }

    // find - all

    @Test
    public void whenAllResourcesAreRetrieved_thenNoExceptions() {
        getAPI().findAll();
    }

    @Test
    public void whenAllResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() {
        // Given
        getAPI().create(createNewEntity());

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        assertThat(allResources, not(Matchers.<T> empty()));
    }

    @Test
    public void whenAllResourcesAreRetrieved_thenResourcesHaveIds() {
        // Given
        this.getAPI().create(createNewEntity());

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        for (final T resource : allResources) {
            assertNotNull(resource.getId());
        }
    }

    // create

    @Test
    public void whenAResourceIsCreated_thenNoExceptions() {
        getAPI().create(createNewEntity());
    }

    // update

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

    // the ORIGINAL

    // find - one

    @Test
    public void givenEntityExists_whenEntityIsRetrieved_thenNoExceptions() {
        final T existingEntity = getAPI().create(createNewEntity());
        getAPI().findOne(existingEntity.getId());
    }

    @Test
    public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenNoExceptions() {
        getAPI().findOne(IDUtils.randomPositiveLong());
    }

    @Test
    public void givenEntityExists_whenEntityIsRetrieved_thenTheResultIsNotNull() {
        final T existingEntity = getAPI().create(createNewEntity());
        final T retrievedEntity = getAPI().findOne(existingEntity.getId());
        assertNotNull(retrievedEntity);
    }

    @Test
    public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenTheResultIsNull() {
        final T retrievedEntity = getAPI().findOne(IDUtils.randomPositiveLong());
        assertNull(retrievedEntity);
    }

    @Test
    public void givenEntityExists_whenEntityIsRetrieved_thenEntityIsRetrievedCorrectly() {
        final T existingEntity = getAPI().create(createNewEntity());
        final T retrievedEntity = getAPI().findOne(existingEntity.getId());
        assertEquals(existingEntity, retrievedEntity);
    }

    // find - all

    @Test
    public void whenEntitiesAreRetrieved_thenNoExceptions() {
        getAPI().findAll();
    }

    @Test
    public void whenEntitiesAreRetrieved_thenTheResultIsNotNull() {
        final List<T> entities = getAPI().findAll();

        assertNotNull(entities);
    }

    @Test
    public void givenAnEntityExists_whenEntitiesAreRetrieved_thenThereIsAtLeastOneEntity() {
        getAPI().create(createNewEntity());

        final List<T> owners = getAPI().findAll();

        assertThat(owners, Matchers.not(Matchers.<T> empty()));
    }

    @Test
    public void givenAnEntityExists_whenEntitiesAreRetrieved_thenTheExistingEntityIsIndeedAmongThem() {
        final T existingEntity = getAPI().create(createNewEntity());

        final List<T> owners = getAPI().findAll();

        assertThat(owners, hasItem(existingEntity));
    }

    // create

    @Test(expected = RuntimeException.class)
    public void whenNullEntityIsCreated_thenException() {
        getAPI().create(null);
    }

    @Test
    public void whenEntityIsCreated_thenNoExceptions() {
        getAPI().create(createNewEntity());
    }

    @Test
    public void whenEntityIsCreated_thenEntityIsRetrievable() {
        final T existingEntity = getAPI().create(createNewEntity());

        assertNotNull(getAPI().findOne(existingEntity.getId()));
    }

    @Test
    public void whenEntityIsCreated_thenSavedEntityIsEqualToOriginalEntity() {
        final T originalEntity = createNewEntity();
        final T savedEntity = getAPI().create(originalEntity);

        assertEquals(originalEntity, savedEntity);
    }

    @Test(expected = DataAccessException.class)
    public void whenEntityWithFailedConstraintsIsCreated_thenException() {
        final T invalidEntity = createNewEntity();
        getEntityOps().invalidate(invalidEntity);

        getAPI().create(invalidEntity);
    }

    // -- specific to the persistence engine

    @Test(expected = DataAccessException.class)
    @Ignore("Hibernate simply ignores the id silently and still saved (tracking this)")
    public void whenEntityWithIdIsCreated_thenDataAccessException() {
        final T entityWithId = createNewEntity();
        entityWithId.setId(IDUtils.randomPositiveLong());

        getAPI().create(entityWithId);
    }

    // update

    @Test(expected = RuntimeException.class)
    public void whenNullEntityIsUpdated_thenException() {
        getAPI().update(null);
    }

    @Test
    public void whenEntityIsUpdated_thenNoExceptions() {
        final T existingEntity = getAPI().create(createNewEntity());

        getAPI().update(existingEntity);
    }

    @Test(expected = DataAccessException.class)
    public void whenEntityIsUpdatedWithFailedConstraints_thenException() {
        final T existingEntity = getAPI().create(createNewEntity());
        getEntityOps().invalidate(existingEntity);

        getAPI().update(existingEntity);
    }

    @Test
    public void whenEntityIsUpdated_thenTheUpdatedAreCorrectlyPersisted() {
        final T existingEntity = getAPI().create(createNewEntity());
        getEntityOps().change(existingEntity);

        getAPI().update(existingEntity);

        final T updatedEntity = getAPI().findOne(existingEntity.getId());
        assertEquals(existingEntity, updatedEntity);
    }

    // delete

    @Test(expected = RuntimeException.class)
    public void givenEntityDoesNotExists_whenEntityIsDeleted_thenException() {
        // When
        getAPI().delete(IDUtils.randomPositiveLong());
    }

    @Test(expected = RuntimeException.class)
    public void whenEntityIsDeletedByNegativeId_thenException() {
        // When
        getAPI().delete(IDUtils.randomNegativeLong());
    }

    @Test
    public void givenEntityExists_whenEntityIsDeleted_thenNoExceptions() {
        // Given
        final T existingLocation = getAPI().create(createNewEntity());

        // When
        getAPI().delete(existingLocation.getId());
    }

    @Test
    public void givenEntityExists_whenEntityIsDeleted_thenEntityIsNoLongerRetrievable() {
        // Given
        final T existingEntity = getAPI().create(createNewEntity());

        // When
        getAPI().delete(existingEntity.getId());

        // Then
        assertNull(getAPI().findOne(existingEntity.getId()));
    }

    // template method

    protected abstract IService<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected abstract T createNewEntity();

}
