package org.rest.common.web.base;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.rest.common.search.ClientOperation.CONTAINS;
import static org.rest.common.search.ClientOperation.ENDS_WITH;
import static org.rest.common.search.ClientOperation.EQ;
import static org.rest.common.search.ClientOperation.NEG_EQ;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.test.contract.ISearchIntegrationTest;
import org.rest.common.util.IDUtils;
import org.rest.common.util.SearchField;
import org.rest.common.util.SearchIntegrationTestUtil;
import org.rest.common.web.util.ClientConstraintsUtil;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@SuppressWarnings("unchecked")
public abstract class AbstractSearchRESTIntegrationTest<T extends INameableEntity> implements ISearchIntegrationTest {

    public AbstractSearchRESTIntegrationTest() {
        super();
    }

    // tests

    @Override
    @Test
    public final void whenSearchByNameIsPerformed_thenNoExceptions() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        getAPI().search(nameConstraint);
    }

    // by id

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenNoExceptions() {
        final T existingResource = getAPI().create(createNewEntity());
        getAPI().searchAsResponse(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), null);
    }

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenSearchOperationIsSuccessful() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final Response searchResponse = getAPI().searchAsResponse(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), null);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenResourceIsFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    @Override
    public final void givenResourceWithIdDoesNotExist_whenResourceIsSearchedById_thenResourceIsNotFound() {
        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createConstraint(EQ, SearchField.id.toString(), randomNumeric(8)));

        // Then
        assertThat(found, Matchers.<T> empty());
    }

    // by name

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenNoExceptions() {
        final T existingResource = getAPI().create(createNewEntity());
        getAPI().searchAsResponse(null, ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenOperationIsSuccessful() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final Response searchResponse = getAPI().searchAsResponse(null, ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenResourceIsFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithNameDoesNotExist_whenResourceIsSearchedByName_thenResourceIsNotFound() {
        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createNameConstraint(EQ, randomAlphabetic(8)));

        // Then
        assertThat(found, Matchers.<T> empty());
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByNegatedNameIsPerformed_thenResourcesAreCorrect() {
        final T existingResource1 = getAPI().create(createNewEntity());
        final T existingResource2 = getAPI().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource1.getName());
        final List<T> searchResults = getAPI().search(nameConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource1));
        assertThat(searchResults, not(hasItem(existingResource2)));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByNameLowerCase_thenResourceIsFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName().toLowerCase()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    // by name - contains

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByContainsExactName_thenNoExceptions() {
        final T existingResource = getAPI().create(createNewEntity());
        getAPI().searchAsResponse(null, ClientConstraintsUtil.createNameConstraint(CONTAINS, existingResource.getName()));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByContainsEntireNameIsPerformed_thenResourceIsFound() {
        final T existingEntity = getAPI().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), CONTAINS, existingEntity.getName());
        final List<T> searchResults = getAPI().search(nameConstraint);

        // Then
        assertThat(searchResults, hasItem(existingEntity));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByContainsPartOfNameIsPerformed_thenResourceIsFound() {
        final T existingEntity = getAPI().create(createNewEntity());
        final String name = existingEntity.getName();
        final String partOfName = name.substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> nameContainsConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), CONTAINS, partOfName);
        final List<T> searchResults = getAPI().search(nameContainsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingEntity));
    }

    // starts with, ends with

    @Override
    @Test
    public final void whenSearchByStartsWithIsPerformed_thenNoExceptions() {
        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ClientOperation.STARTS_WITH, randomAlphabetic(8));
        getAPI().search(nameConstraint);
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByStartsWithEntireNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithEntireKeyIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.STARTS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByStartsWithPartOfNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithPartOfKeyIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.STARTS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void whenSearchByEndsWithIsPerformed_thenNoExceptions() {
        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ENDS_WITH, randomAlphabetic(8));
        getAPI().search(nameConstraint);
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByEndsWithEntireNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByEndsWithEntireKeyIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    @Test
    public final void givenResourceExists_whenSearchByStartsWithPartOfLowerCaseNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithPartOfLowerCaseNameIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    // by id and name

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenOperationIsSuccessful() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final Response searchResponse = getAPI().searchAsResponse(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenResourceIsFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndIncorrectName_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), ClientConstraintsUtil.createNameConstraint(EQ, randomAlphabetic(8)));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndCorrectName_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createIdConstraint(EQ, IDUtils.randomPositiveLong()), ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndIncorrectName_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createIdConstraint(EQ, IDUtils.randomPositiveLong()), ClientConstraintsUtil.createNameConstraint(EQ, randomAlphabetic(8)));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    // by negated id, name

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedName_thenOperationIsSuccessful() {
        final T existingResource = getAPI().create(createNewEntity());

        final Triple<String, ClientOperation, String> negatedNameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), NEG_EQ, existingResource.getName());

        // When
        final Response searchResponse = getAPI().searchAsResponse(null, negatedNameConstraint);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedId_thenOperationIsSuccessful() {
        final T existingResource = getAPI().create(createNewEntity());

        final Triple<String, ClientOperation, String> negatedIdConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), NEG_EQ, existingResource.getId().toString());

        // When
        final Response searchResponse = getAPI().searchAsResponse(negatedIdConstraint, null);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedId_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createIdConstraint(NEG_EQ, existingResource.getId()));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourcesExists_whenResourceIsSearchedByNegatedId_thenTheOtherResourcesAreFound() {
        final T existingResource1 = getAPI().create(createNewEntity());
        final T existingResource2 = getAPI().create(createNewEntity());

        // When
        final List<T> found = getAPI().search(ClientConstraintsUtil.createIdConstraint(NEG_EQ, existingResource1.getId()));

        // Then
        assertThat(found, hasItem(existingResource2));
    }

    @Override
    @Test
    public final void givenResourceAndOtherResourcesExists_whenResourceIsSearchedByNegatedName_thenResourcesAreFound() {
        final T existingResource1 = getAPI().create(createNewEntity());
        final T existingResource2 = getAPI().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), NEG_EQ, existingResource1.getName());
        final List<T> searchResults = getAPI().search(nameConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource1)));
        assertThat(searchResults, hasItem(existingResource2));
    }

    @Override
    @Test
    public final void givenResourceAndOtherResourcesExists_whenResourceIsSearchedByNegatedId_thenResourcesAreFound() {
        final T existingResource1 = getAPI().create(createNewEntity());
        final T existingResource2 = getAPI().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), NEG_EQ, existingResource1.getId().toString());
        final List<T> searchResults = getAPI().search(idConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource1)));
        assertThat(searchResults, hasItem(existingResource2));
    }

    // with paging

    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNameWithPaging_then200IsReceived() {
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        final Response searchResponse = getAPI().searchAsResponse(null, nameConstraint, 0, 2);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Test
    public final void givenResourcesExists_whenResourceIsSearchedByNameWithPagingOfSize2_thenMax2ResourcesAreReceived() {
        final T existingResource1 = getAPI().create(createNewEntity());
        getAPI().create(createNewEntity());
        getAPI().create(createNewEntity());
        getAPI().create(createNewEntity());

        // When
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), NEG_EQ, existingResource1.getName());
        final List<T> searchResults = getAPI().searchPaginated(null, nameConstraint, 0, 2);

        // Then
        assertThat(searchResults.size(), is(2));
    }

    // template

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    protected abstract IRESTTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected final RequestSpecification givenAuthenticated() {
        return getAPI().givenAuthenticated();
    }

}
