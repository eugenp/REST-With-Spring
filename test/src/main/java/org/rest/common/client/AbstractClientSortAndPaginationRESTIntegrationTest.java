package org.rest.common.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.rest.common.client.template.IClientTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.util.SearchField;
import org.rest.common.util.order.OrderById;
import org.rest.common.util.order.OrderByName;
import org.rest.common.web.WebConstants;
import org.springframework.data.domain.Sort;

public abstract class AbstractClientSortAndPaginationRESTIntegrationTest<T extends INameableEntity> {

    public AbstractClientSortAndPaginationRESTIntegrationTest() {
        super();
    }

    // tests

    @Test
    public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned() {
        getAPI().createAsURI(getEntityOps().createNewEntity());

        // When
        final List<T> allPaginated = getAPI().findAllPaginated(0, 1);

        // Then
        assertFalse(allPaginated.isEmpty());
    }

    // find - all - sorting

    @Test
    public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsURI(getEntityOps().createNewEntity());
        getAPI().createAsURI(getEntityOps().createNewEntity());

        // When
        final List<T> resourcesSorted = getAPI().findAllSorted("name", Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesSorted));
    }

    @Test
    public final void whenResourcesAreRetrievedSortedDescById_thenNoExceptions() {
        getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());
    }

    @Test
    public final void whenResourcesAreRetrievedSortedAscById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.ASC.name());

        assertTrue(new OrderById<T>().isOrdered(resourcesOrderedById));
    }

    @Test
    public final void whenResourcesAreRetrievedSortedDescById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());

        assertTrue(new OrderById<T>().reverse().isOrdered(resourcesOrderedById));
    }

    // find - all - pagination and sorting

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_thenNoExceptions() {
        getAPI().findAllPaginatedAndSorted(0, 41, "name", null);
    }

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsURI(getEntityOps().createNewEntity());
        getAPI().createAsURI(getEntityOps().createNewEntity());

        // When
        final List<T> resourcesPaginatedAndSorted = getAPI().findAllPaginatedAndSorted(0, 4, "name", Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesPaginatedAndSorted));
    }

    // template method

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    protected abstract IClientTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected final String getURI() {
        return getAPI().getURI() + WebConstants.PATH_SEP;
    }

}
