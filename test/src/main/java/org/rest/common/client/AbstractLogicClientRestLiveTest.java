package org.rest.common.client;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.rest.common.search.ClientOperation.EQ;
import static org.rest.common.search.ClientOperation.NEG_EQ;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.template.IClientTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.SearchField;

public abstract class AbstractLogicClientRestLiveTest<T extends INameableEntity> extends AbstractRawLogicClientRestLiveTest<T> {

    public AbstractLogicClientRestLiveTest() {
        super();
    }

    // tests

    // find one - by name

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
    @Ignore("temp")
    /**/public final void givenExistingResourceHasSpaceInName_whenResourceIsRetrievedByName_thenFoundResourceIsCorrect() {
        final T newEntity = createNewEntity();
        newEntity.setName(randomAlphabetic(4) + " " + randomAlphabetic(4));

        // Given
        final T existingResource = getApi().create(newEntity);

        // When
        final T resourceByName = getApi().findByName(existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    // find - all (+sorting + pagination)

    // search one - by attributes

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        getApi().searchOne(nameConstraint);
    }

    @Test
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenResourceIsFound() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        final T resourceByName = getApi().searchOne(nameConstraint);

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    @SuppressWarnings("unchecked")
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        final T resourceByName = getApi().searchOne(nameConstraint);

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    @Test
    @SuppressWarnings("unchecked")
    @Ignore("bug in RestTemplate")
    public final void givenResourceExists_whenResourceIsSearchedByNagatedNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), NEG_EQ, existingResource.getName());
        getApi().searchAll(nameConstraint);

        // Then
    }

    // template method

    @Override
    protected abstract IClientTemplate<T> getApi();

}
