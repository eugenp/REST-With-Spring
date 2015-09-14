package org.baeldung.test.common.util;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.baeldung.common.interfaces.IOperations;
import org.baeldung.common.search.ClientOperation;
import org.baeldung.common.util.SearchField;

@SuppressWarnings("unchecked")
public final class SearchIntegrationTestUtil {

    private SearchIntegrationTestUtil() {
        throw new AssertionError();
    }

    // API

    public static <T extends Serializable> void givenResourceExists_whenSearchByStartsWithEntireKeyIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op, final String value) {
        final T existingResource = api.create(newEntity);

        // When
        final ImmutableTriple<String, ClientOperation, String> constraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, value);
        final List<T> searchResults = api.searchAll(constraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    public static <T extends Serializable> void givenResourceExists_whenSearchByStartsWithPartOfKeyIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op, final String value) {
        final T existingResource = api.create(newEntity);
        final String partOfValue = value.substring(0, 2);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.searchAll(containsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    public static <T extends Serializable> void givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op, final String value) {
        final T existingResource = api.create(newEntity);
        final String partOfValue = value.substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.searchAll(containsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    public static <T extends Serializable> void givenResourceExists_whenSearchByEndsWithEntireKeyIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op, final String value) {
        final T existingEntity = api.create(newEntity);

        // When
        final ImmutableTriple<String, ClientOperation, String> constraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, value);
        final List<T> searchResults = api.searchAll(constraint);

        // Then
        assertThat(searchResults, hasItem(existingEntity));
    }

    public static <T extends Serializable> void givenResourceExists_whenSearchByEndsWithIncorrectPartOfKeyIsPerformed_thenResourceIsNotFound(final IOperations<T> api, final T existingEntity, final SearchField key, final ClientOperation op,
            final String value) {
        final String partOfValue = value.substring(2, 5);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.searchAll(containsConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingEntity)));
    }

    public static <T extends Serializable> void givenResourceExists_whenSearchByStartsWithPartOfLowerCaseNameIsPerformed_thenResourceIsFound(final IOperations<T> api, final T newEntity, final SearchField key, final ClientOperation op, final String value) {
        final T existingResource = api.create(newEntity);
        final String partOfValue = value.substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(key.toString(), op, partOfValue);
        final List<T> searchResults = api.searchAll(containsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

}
