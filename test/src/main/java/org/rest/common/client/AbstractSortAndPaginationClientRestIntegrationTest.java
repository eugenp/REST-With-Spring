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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "client", "test", "mime_json" })
public abstract class AbstractSortAndPaginationClientRestIntegrationTest<T extends INameableEntity> {

    public AbstractSortAndPaginationClientRestIntegrationTest() {
        super();
    }

    // tests

    // find - all - pagination

    @Test
    /**/public final void whenResourcesAreRetrievedPaginated_thenNoExceptions() {
        getAPI().findAllPaginated(1, 1);
    }

    @Test
    /**/public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned() {
        getAPI().createAsUri(createNewEntity(), null);

        // When
        final List<T> allPaginated = getAPI().findAllPaginated(0, 1);

        // Then
        assertFalse(allPaginated.isEmpty());
    }

    // find - all - sorting

    @Test
    /**/public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsUri(createNewEntity(), null);
        getAPI().createAsUri(createNewEntity(), null);

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
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.name(), Sort.Direction.DESC.name());

        assertTrue(new OrderById<T>().reverse().isOrdered(resourcesOrderedById));
    }

    // find - all - pagination and sorting

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenNoExceptions() {
        getAPI().findAllPaginatedAndSorted(0, 41, SearchField.name.name(), Sort.Direction.ASC.name());
    }

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsUri(createNewEntity(), null);
        getAPI().createAsUri(createNewEntity(), null);

        // When
        final List<T> resourcesPaginatedAndSorted = getAPI().findAllPaginatedAndSorted(0, 4, SearchField.name.name(), Sort.Direction.ASC.name());

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
        return getAPI().getUri() + WebConstants.PATH_SEP;
    }

}
