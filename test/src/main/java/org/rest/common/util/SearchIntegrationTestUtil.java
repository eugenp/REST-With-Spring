package org.rest.common.util;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.rest.common.IOperations;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.SearchField;

@SuppressWarnings("unchecked")
public final class SearchIntegrationTestUtil {

    private SearchIntegrationTestUtil() {
        throw new AssertionError();
    }

    // API

    public static <T extends IEntity> void givenResourceExists_whenSearchByStartsWithEntireKeyIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op,
            final String value) {
        final T existingResource = api.create(newEntity);

        // When
        final ImmutableTriple<String, ClientOperation, String> constraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, value);
        final List<T> searchResults = api.search(constraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    public static <T extends IEntity> void givenResourceExists_whenSearchByStartsWithPartOfKeyIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op,
            final String value) {
        final T existingResource = api.create(newEntity);
        final String partOfValue = value.substring(0, 2);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.search(containsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    public static <T extends IEntity> void givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op,
            final String value) {
        final T existingResource = api.create(newEntity);
        final String partOfValue = value.substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.search(containsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    public static <T extends IEntity> void givenResourceExists_whenSearchByEndsWithEntireKeyIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op,
            final String value) {
        final T existingEntity = api.create(newEntity);

        // When
        final ImmutableTriple<String, ClientOperation, String> constraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, value);
        final List<T> searchResults = api.search(constraint);

        // Then
        assertThat(searchResults, hasItem(existingEntity));
    }

    public static <T extends IEntity> void givenResourceExists_whenSearchByEndsWithIncorrectPartOfKeyIsPerformed_thenResourceIsNotFound(final IOperations<T> api, final T newEntity, final SearchField key,
            final ClientOperation op, final String value) {
        final T existingResource = api.create(newEntity);
        final String partOfValue = value.substring(2, 5);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.search(containsConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource)));
    }

    public static <T extends IEntity> void givenResourceExists_whenSearchByStartsWithPartOfLowerCaseNameIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key,
            final ClientOperation op, final String value) {
        final T existingResource = api.create(newEntity);
        final String partOfValue = value.substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.search(containsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

}
