package org.rest.persistence.service;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.util.IDUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

/**
 * A base class for service layer unit tests.
 */
public abstract class AbstractServiceUnitTest<T extends IEntity> {

    // fixtures

    public void before() {
	final AbstractService<T> instance = (AbstractService<T>) getService();
	instance.eventPublisher = mock(ApplicationEventPublisher.class);
    }

    // tests

    @Test
    public void whenServiceIsInitialized_thenNoException() {
	// When
	// Then
    }

    // create

    @Test(expected = NullPointerException.class)
    public void whenCreateIsTriggeredForNullEntity_thenException() {
	// When
	getService().create(null);

	// Then
    }

    @Test
    public void whenCreateIsTriggered_thenNoException() {
	// When
	getService().create(this.createNewEntity());

	// Then
    }

    @Test
    public void whenCreatingANewEntity_thenEntityIsSaved() {
	// Given
	final T entity = createNewEntity();
	stubDaoSave(entity);

	// When
	getService().create(entity);

	// Then
	verify(getDAO()).save(entity);
    }

    // update

    @Test
    public void whenUpdateIsTriggered_thenNoException() {
	// When
	getService().update(this.createNewEntity());

	// Then
    }

    @Test(expected = NullPointerException.class)
    public void givenNullEntity_whenUpdate_thenException() {
	getService().update(null);
    }

    @Test
    public void whenUpdateIsTriggered_thenEntityIsUpdated() {
	// When
	final T entity = this.createNewEntity();
	getService().update(entity);

	// Then
	verify(getDAO()).save(entity);
    }

    // find - paged

    @Test
    public void whenPageOfEntitiesIsRetrieved_thenResultIsCorrect() {
	// Given
	final PageRequest pageRequest = new PageRequest(1, 10);
	final Page<T> page = new PageImpl<T>(Lists.<T> newArrayList(), pageRequest, 10L);
	when(getDAO().findAll(eq(pageRequest))).thenReturn(page);

	// When
	final Page<T> found = getService().findPaginated(1, 10, null);

	// Then
	assertSame(page, found);
    }

    // find - all

    @Test
    public void whenGetAllIsTriggered_thenNoException() {
	// When
	getService().findAll();

	// Then
    }

    @Test
    public void whenGetAllIsTriggered_thenAllEntitiesAreRetrieved() {
	// When
	getService().findAll();

	// Then
	verify(getDAO()).findAll();
    }

    // delete

    /**
     * - note: the responsibility of ensuring data integrity belongs to the database; because this is an unit test, then no exception is thrown
     */
    @Test
    public void givenResourceDoesNotExist_whenDeleteIsTriggered_thenNoExceptions() {
	// When
	getService().delete(IDUtils.randomPositiveLong());

	// Then
    }

    @Test
    public void givenResourceExists_whenDeleteIsTriggered_thenNoExceptions() {
	final long id = IDUtils.randomPositiveLong();

	// Given
	when(getDAO().findOne(id)).thenReturn(this.createNewEntity());

	// When
	getService().delete(id);

	// Then
    }

    @Test
    public void givenResourceExists_whenDeleteIsTriggered_thenEntityIsDeleted() {
	final long id = IDUtils.randomPositiveLong();

	// Given
	when(getDAO().findOne(id)).thenReturn(this.createNewEntity());

	// When
	getService().delete(id);

	// Then
	verify(getDAO()).delete(id);
    }

    // delete - all

    @Test
    public void whenDeleteAllEntities_thenEntitiesAreDeleted() {
	// When
	getService().deleteAll();

	// Then
	verify(getDAO()).deleteAll();
    }

    // template method

    protected abstract IService<T> getService();

    protected abstract JpaRepository<T, Long> getDAO();

    //

    protected abstract T createNewEntity();

    /**
     * Creates and returns the instance of entity that is existing (ie ID is not null).
     * 
     * @return the created entity
     */
    protected T createSimulatedExistingEntity() {
	final T entity = createNewEntity();
	entity.setId(IDUtils.randomPositiveLong());
	return entity;
    }

    //

    protected void stubDaoSave(final T entity) {
	when(getDAO().save(entity)).thenReturn(entity);
    }

}
