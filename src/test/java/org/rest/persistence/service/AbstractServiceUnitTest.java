package org.rest.persistence.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.persistence.event.BeforeEntityCreatedEvent;
import org.rest.persistence.event.EntityCreatedEvent;
import org.rest.persistence.event.EntityUpdatedEvent;
import org.rest.util.IDUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.Lists;

/**
 * A base class for service layer unit tests.
 */
public abstract class AbstractServiceUnitTest<T extends IEntity> {

    protected ApplicationEventPublisher eventPublisher;

    // tests

    public void before() {
	when(getDAO().findAll()).thenReturn(Lists.<T> newArrayList());
	eventPublisher = mock(ApplicationEventPublisher.class);
	ReflectionTestUtils.setField(getService(), "eventPublisher", eventPublisher);
    }

    @Test
    public final void whenServiceIsInitialized_thenNoException() {
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
	getService().create(stubDaoSave(createNewEntity()));

	// Then
    }

    @Test
    public void whenCreatingANewEntity_thenEntityIsSaved() {
	// Given
	final T entity = stubDaoSave(createNewEntity());

	// When
	getService().create(entity);

	// Then
	verify(getDAO()).save(entity);
    }

    @Test
    public void whenCreatingANewEntity_thenBeforeCreateEventIsPublished() {
	// Given
	final T entity = createNewEntity();
	stubDaoSave(entity);

	// When
	getService().create(entity);

	// Then
	verify(getEventPublisher()).publishEvent(isA(BeforeEntityCreatedEvent.class));
    }

    @Test
    public void whenCreatingANewEntity_thenEventIsPublished() {
	// Given
	final T entity = createNewEntity();
	stubDaoSave(entity);

	// When
	getService().create(entity);

	// Then
	verify(getEventPublisher()).publishEvent(isA(EntityCreatedEvent.class));
    }

    // update

    @Test
    public void whenUpdateIsTriggered_thenNoException() {
	// When
	getService().update(givenEntityExists(stubDaoSave(createSimulatedExistingEntity())));

	// Then
    }

    @Test(expected = NullPointerException.class)
    public void givenNullEntity_whenUpdate_thenException() {
	getService().update(null);
    }

    @Test
    public void whenUpdateIsTriggered_thenEntityIsUpdated() {
	// When
	final T entity = createSimulatedExistingEntity();
	getService().update(entity);

	// Then
	verify(getDAO()).save(entity);
    }

    @Test
    public void givenEntity_whenUpdate_thenEventIsPublished() {
	// Given
	final T entity = createSimulatedExistingEntity();
	stubDaoSave(entity);

	// When
	getService().update(entity);

	// Then
	verify(getEventPublisher()).publishEvent(isA(EntityUpdatedEvent.class));
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

    // find - one

    @Test
    public void whenEntityByIdIsFound_thenItIsReturned() {
	// Given
	final T entity = createSimulatedExistingEntity();
	givenEntityExists(entity);

	// When
	final T found = getService().findOne(entity.getId());

	// Then
	assertThat(found, is(equalTo(entity)));
    }

    // delete

    /**
     * - note: the responsibility of ensuring data integrity belongs to the database; because this is an unit test, then no exception is thrown
     */
    @Test
    public void givenResourceDoesNotExist_whenDeleteIsTriggered_thenNoExceptions() {
	final long randomId = IDUtils.randomPositiveLong();
	givenEntityExists(randomId);

	// When
	getService().delete(randomId);

	// Then
    }

    @Test
    public void givenResourceExists_whenDeleteIsTriggered_thenNoExceptions() {
	final long id = IDUtils.randomPositiveLong();

	// Given
	givenEntityExists(id);

	// When
	getService().delete(id);

	// Then
    }

    @Test
    public void givenResourceExists_whenDeleteIsTriggered_thenEntityIsDeleted() {
	// Given
	final long id = IDUtils.randomPositiveLong();
	final T entityToBeDeleted = givenEntityExists(id);

	// When
	getService().delete(id);

	// Then
	verify(getDAO()).delete(entityToBeDeleted);
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

    protected T givenEntityExists(final long id) {
	final T entity = createNewEntity();
	entity.setId(id);
	when(getDAO().findOne(id)).thenReturn(entity);
	return entity;
    }

    protected T givenEntityExists(final T entity) {
	when(getDAO().findOne(entity.getId())).thenReturn(entity);
	return entity;
    }

    protected final T stubDaoSave(final T entity) {
	when(getDAO().save(entity)).thenReturn(entity);
	return entity;
    }

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

    /**
     * Gets the service that is need to be tested.
     * 
     * @return the service.
     */
    protected abstract IService<T> getService();

    /**
     * Gets the DAO mock.
     * 
     * @return the DAO mock.
     */
    protected abstract PagingAndSortingRepository<T, Long> getDAO();

    /**
     * Gets the application event publisher mock.
     * 
     * @return the event publisher mock.
     */
    protected abstract ApplicationEventPublisher getEventPublisher();

    //

    protected abstract T createNewEntity();

    protected abstract void changeEntity(final T entity);

}
