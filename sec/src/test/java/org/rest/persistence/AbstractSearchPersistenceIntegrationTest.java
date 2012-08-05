package org.rest.persistence;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.rest.common.search.ClientOperation.EQ;
import static org.rest.common.search.ClientOperation.NEG_EQ;
import static org.rest.common.util.SearchCommonUtil.NAME;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.client.IEntityOperations;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.IService;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.SearchCommonUtil;
import org.rest.common.util.SearchField;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.test.contract.ISearchIntegrationTest;
import org.rest.util.IDUtils;
import org.rest.util.SearchIntegrationTestUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class AbstractSearchPersistenceIntegrationTest<T extends INameableEntity> implements ISearchIntegrationTest {

    // search/filter

    @Override
    @Test
    public final void whenSearchByNameIsPerformed_thenNoExceptions() {
        final T existingEntity = getAPI().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(NAME, EQ, existingEntity.getName());
        getAPI().search(nameConstraint);
    }

    // by id

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenNoExceptions() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());
        getAPI().search(createIdConstraint(EQ, existingResource.getId()));
    }

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenSearchOperationIsSuccessful() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), EQ, existingResource.getId().toString());
        final List<T> searchResults = getAPI().search(idConstraint);

        // Then
        assertThat(searchResults, Matchers.not(Matchers.<T> empty()));
    }

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenResourceIsFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final List<T> found = getAPI().search(createIdConstraint(EQ, existingResource.getId()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithIdDoesNotExist_whenResourceIsSearchedById_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), EQ, IDUtils.randomPositiveLongAsString());
        final List<T> searchResults = getAPI().search(idConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource)));
    }

    // by name

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenNoExceptions() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        getAPI().search(nameConstraint);
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenOperationIsSuccessful() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        final List<T> searchResults = getAPI().search(nameConstraint);

        // Then
        assertThat(searchResults, Matchers.not(Matchers.<T> empty()));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenResourceIsFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        final List<T> searchResults = getAPI().search(nameConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithNameDoesNotExist_whenResourceIsSearchedByName_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, randomAlphabetic(8));
        final List<T> searchResults = getAPI().search(nameConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByNegatedNameIsPerformed_thenResourcesAreCorrect() {
        final T existingResource1 = getAPI().create(getEntityOperations().createNewEntity());
        final T existingResource2 = getAPI().create(getEntityOperations().createNewEntity());

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
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final List<T> found = getAPI().search(createNameConstraint(EQ, existingResource.getName().toLowerCase()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    // by id and name

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenOperationIsSuccessful() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), EQ, existingResource.getId().toString());
        final List<T> searchResults = getAPI().search(nameConstraint, idConstraint);

        // Then
        assertThat(searchResults, Matchers.not(Matchers.<T> empty()));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenResourceIsFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), EQ, existingResource.getId().toString());
        final List<T> searchResults = getAPI().search(nameConstraint, idConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndCorrectName_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), EQ, IDUtils.randomPositiveLongAsString());
        final List<T> searchResults = getAPI().search(nameConstraint, idConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndIncorrectName_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, randomAlphabetic(8));
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), EQ, existingResource.getId().toString());
        final List<T> searchResults = getAPI().search(nameConstraint, idConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndIncorrectName_thenResourceIsNotFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final List<T> found = getAPI().search(createIdConstraint(EQ, IDUtils.randomPositiveLong()), createNameConstraint(EQ, randomAlphabetic(8)));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    // by negated id, name

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedId_thenOperationIsSuccessful() {
        // Given
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final Triple<String, ClientOperation, String> negatedIdConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), NEG_EQ, existingResource.getId().toString());
        final List<T> searchResults = getAPI().search(negatedIdConstraint);

        // Then
        assertThat(searchResults, notNullValue());
    }

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedName_thenOperationIsSuccessful() {
        // Given
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final Triple<String, ClientOperation, String> negatedIdConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), NEG_EQ, existingResource.getName());
        final List<T> searchResults = getAPI().search(negatedIdConstraint);

        // Then
        assertThat(searchResults, notNullValue());
    }

    @Override
    @Test
    public final void givenResourceAndOtherResourcesExists_whenResourceIsSearchedByNegatedName_thenResourcesAreFound() {
        final T existingResource1 = getAPI().create(getEntityOperations().createNewEntity());
        final T existingResource2 = getAPI().create(getEntityOperations().createNewEntity());

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
        final T existingResource1 = getAPI().create(getEntityOperations().createNewEntity());
        final T existingResource2 = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), NEG_EQ, existingResource1.getId().toString());
        final List<T> searchResults = getAPI().search(idConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource1)));
        assertThat(searchResults, hasItem(existingResource2));
    }

    @Override
    public final void givenResourceExists_whenResourceIsSearchedByNegatedId_thenResourceIsNotFound() {
        // Given
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final Triple<String, ClientOperation, String> negatedIdConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), NEG_EQ, existingResource.getId().toString());
        final List<T> searchResults = getAPI().search(negatedIdConstraint, null);

        // Then
        assertThat(searchResults, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourcesExists_whenResourceIsSearchedByNegatedId_thenTheOtherResourcesAreFound() {
        final T existingResource1 = getAPI().create(getEntityOperations().createNewEntity());
        final T existingResource2 = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final List<T> found = getAPI().search(createIdConstraint(NEG_EQ, existingResource1.getId()));

        // Then
        assertThat(found, hasItem(existingResource2));
    }

    // by name - contains

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByContainsExactName_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final Triple<String, ClientOperation, String> nameConstraint = createNameConstraint(ClientOperation.CONTAINS, existingResource.getName());
        getAPI().search(nameConstraint);
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByContainsEntireNameIsPerformed_thenResourceIsFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ClientOperation.CONTAINS, existingResource.getName());
        final List<T> searchResults = getAPI().search(nameConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByContainsPartOfNameIsPerformed_thenResourceIsFound() {
        final T existingResource = getAPI().create(getEntityOperations().createNewEntity());
        final String name = existingResource.getName();
        final String partOfName = name.substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> nameContainsConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ClientOperation.CONTAINS, partOfName);
        final List<T> searchResults = getAPI().search(nameContainsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
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
        final T newEntity = getEntityOperations().createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithEntireKeyIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.STARTS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByStartsWithPartOfNameIsPerformed_thenResourceIsFound() {
        final T newEntity = getEntityOperations().createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithPartOfKeyIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.STARTS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void whenSearchByEndsWithIsPerformed_thenNoExceptions() {
        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ClientOperation.ENDS_WITH, randomAlphabetic(8));
        getAPI().search(nameConstraint);
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByEndsWithEntireNameIsPerformed_thenResourceIsFound() {
        final T newEntity = getEntityOperations().createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByEndsWithEntireKeyIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound() {
        final T newEntity = getEntityOperations().createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound(getAPI(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    @Test
    public final void givenResourceExists_whenSearchByEndsWithIncorrectPartOfNameIsPerformed_thenResourceIsNotFound() {
        final T newEntity = getEntityOperations().createNewEntity();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByEndsWithIncorrectPartOfKeyIsPerformed_thenResourceIsNotFound(getAPI(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    @Test
    public final void givenResourceExists_whenSearchByStartsWithPartOfLowerCaseNameIsPerformed_thenResourceIsFound() {
        final T newEntity = getEntityOperations().createNewEntity();
        final T existingResource = getAPI().create(newEntity);
        final String partOfValue = newEntity.getName().substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> containsConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), ClientOperation.ENDS_WITH, partOfValue);
        final List<T> searchResults = getAPI().search(containsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource));
    }

    // template method

    protected abstract IService<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOperations();

    protected abstract T createNewEntity();

    // util

    final Triple<String, ClientOperation, String> createNameConstraint(final ClientOperation operation, final String nameValue) {
        return new ImmutableTriple<String, ClientOperation, String>(SearchCommonUtil.NAME, operation, nameValue);
    }

    final Triple<String, ClientOperation, String> createIdConstraint(final ClientOperation operation, final Long idValue) {
        return new ImmutableTriple<String, ClientOperation, String>(SearchCommonUtil.ID, operation, idValue.toString());
    }

}
