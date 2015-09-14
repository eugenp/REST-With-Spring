package org.baeldung.test.common.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.baeldung.common.persistence.model.INameableEntity;
import org.baeldung.common.persistence.service.IService;
import org.baeldung.common.util.SearchField;
import org.baeldung.common.util.order.OrderByName;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.domain.Sort;

public abstract class AbstractServiceIntegrationTest<T extends INameableEntity> extends AbstractRawServiceIntegrationTest<T> {

    // tests

    // find - one - by name

    @Test
    /**/public final void givenResourceExists_whenResourceIsRetrievedByName_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getApi().findByName(existingResource.getName());
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsRetrievedByName_thenResourceIsFound() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final T resourceByName = getApi().findByName(existingResource.getName());

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsRetrievedByName_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getApi().create(createNewEntity());
        // When
        final T resourceByName = getApi().findByName(existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    @Test
    @Ignore
    /**/public final void givenExistingResourceHasSpaceInName_whenResourceIsRetrievedByName_thenFoundResourceIsCorrect() {
        final T newEntity = createNewEntity();
        // / newEntity.setName(randomAlphabetic(4) + " " + randomAlphabetic(4));

        // Given
        final T existingResource = getApi().create(newEntity);

        // When
        final T resourceByName = getApi().findByName(existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    // find - all - sorting

    @Test
    /**/public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        persistNewEntity();
        persistNewEntity();

        // When
        final List<T> resourcesSorted = getApi().findAllSorted(SearchField.name.name(), Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesSorted));
    }

    // find - all - pagination and sorting

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenNoExceptions() {
        getApi().findAllPaginatedAndSorted(0, 41, SearchField.name.name(), Sort.Direction.DESC.name());
    }

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenResourcesAreIndeedOrdered() {
        persistNewEntity();
        persistNewEntity();

        // When
        final List<T> resourcesPaginatedAndSorted = getApi().findAllPaginatedAndSorted(0, 4, SearchField.name.name(), Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesPaginatedAndSorted));
    }

    // template method

    @Override
    protected abstract IService<T> getApi();

}
