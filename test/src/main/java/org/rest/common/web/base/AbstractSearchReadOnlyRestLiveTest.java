package org.rest.common.web.base;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertThat;
import static org.rest.common.search.ClientOperation.ENDS_WITH;
import static org.rest.common.search.ClientOperation.EQ;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.rest.common.client.template.IRestTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.SearchField;
import org.rest.common.web.util.ClientConstraintsUtil;
import org.springframework.test.context.ActiveProfiles;

@SuppressWarnings("unchecked")
@ActiveProfiles({ "client", "test", "mime_json" })
public abstract class AbstractSearchReadOnlyRestLiveTest<T extends INameableEntity> {

    public AbstractSearchReadOnlyRestLiveTest() {
        super();
    }

    // tests

    // by id

    public final void givenResourceWithIdDoesNotExist_whenResourceIsSearchedById_thenResourceIsNotFound() {
        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createConstraint(EQ, SearchField.id.toString(), randomNumeric(8)));

        // Then
        assertThat(found, Matchers.<T> empty());
    }

    // by name

    @Test
    public final void givenResourceWithNameDoesNotExist_whenResourceIsSearchedByName_thenResourceIsNotFound() {
        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createNameConstraint(EQ, randomAlphabetic(8)));

        // Then
        assertThat(found, Matchers.<T> empty());
    }

    // starts with, ends with

    @Test
    public final void whenSearchByStartsWithIsPerformed_thenNoExceptions() {
        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ClientOperation.STARTS_WITH, randomAlphabetic(8));
        getApi().searchAll(nameConstraint);
    }

    @Test
    public final void whenSearchByEndsWithIsPerformed_thenNoExceptions() {
        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ENDS_WITH, randomAlphabetic(8));
        getApi().searchAll(nameConstraint);
    }

    // template

    protected abstract IRestTemplate<T> getApi();

}
