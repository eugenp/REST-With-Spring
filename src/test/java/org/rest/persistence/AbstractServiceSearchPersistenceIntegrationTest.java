package org.rest.persistence;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.rest.util.SearchCommonUtil.ID;
import static org.rest.util.SearchCommonUtil.NAME;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.rest.common.INameableEntity;
import org.rest.persistence.service.IService;
import org.rest.util.IDUtils;
import org.rest.util.SearchCommonUtil;

@SuppressWarnings( "unchecked" )
public abstract class AbstractServiceSearchPersistenceIntegrationTest< T extends INameableEntity >{
	
	// search/filter
	
	@Test
	public final void whenSearchByNameIsPerformed_thenNoExceptions(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( NAME, existingEntity.getName() );
		getService().search( nameConstraint );
	}
	
	// search - by id
	
	@Test
	public final void givenEntityWithIdExists_whenSearchByIdIsPerformed_thenResultIsFound(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, Long > idConstraint = new ImmutablePair< String, Long >( ID, existingEntity.getId() );
		final List< T > searchResults = getService().search( idConstraint );
		
		// Then
		assertThat( searchResults, hasItem( existingEntity ) );
	}
	
	@Test
	public final void givenEntityWithIdDoesNotExist_whenSearchByIdIsPerformed_thenResultIsNotFound(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, Long > idConstraint = new ImmutablePair< String, Long >( SearchCommonUtil.ID, IDUtils.randomPositiveLong() );
		final List< T > searchResults = getService().search( idConstraint );
		
		// Then
		assertThat( searchResults, not( hasItem( existingEntity ) ) );
	}
	
	// search - by name
	
	@Test
	public final void givenEntityWithNameExists_whenSearchByNameIsPerformed_thenResultIsFound(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( NAME, existingEntity.getName() );
		final List< T > searchResults = getService().search( nameConstraint );
		
		// Then
		assertThat( searchResults, hasItem( existingEntity ) );
	}
	
	@Test
	public final void givenEntityWithNameDoesNotExist_whenSearchByNameIsPerformed_thenResultIsNotFound(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( NAME, randomAlphabetic( 8 ) );
		final List< T > searchResults = getService().search( nameConstraint );
		
		// Then
		assertThat( searchResults, not( hasItem( existingEntity ) ) );
	}
	
	@Test
	public final void givenEntitiesExists_whenSearchByNegatedNameIsPerformed_thenResultsAreCorrect(){
		final T existingEntity1 = getService().create( createNewEntity() );
		final T existingEntity2 = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( SearchCommonUtil.NAME, existingEntity1.getName() );
		final List< T > searchResults = getService().search( nameConstraint );
		
		// Then
		assertThat( searchResults, hasItem( existingEntity1 ) );
		assertThat( searchResults, not( hasItem( existingEntity2 ) ) );
	}
	
	// search - by id and name
	
	@Test
	public final void givenEntityExists_whenSearchIsPerformedByIdAndName_thenResultIsFound(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( NAME, existingEntity.getName() );
		final ImmutablePair< String, Long > idConstraint = new ImmutablePair< String, Long >( ID, existingEntity.getId() );
		final List< T > searchResults = getService().search( nameConstraint, idConstraint );
		
		// Then
		assertThat( searchResults, hasItem( existingEntity ) );
	}
	
	@Test
	public final void givenEntityExists_whenSearchIsPerformedByIncorrectIdAndCorrectName_thenResultIsNotFound(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( NAME, existingEntity.getName() );
		final ImmutablePair< String, Long > idConstraint = new ImmutablePair< String, Long >( ID, IDUtils.randomPositiveLong() );
		final List< T > searchResults = getService().search( nameConstraint, idConstraint );
		
		// Then
		assertThat( searchResults, not( hasItem( existingEntity ) ) );
	}
	
	@Test
	public final void givenEntityExists_whenSearchIsPerformedByCorrectIdAndIncorrectName_thenResultIsNotFound(){
		final T existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( NAME, randomAlphabetic( 8 ) );
		final ImmutablePair< String, Long > idConstraint = new ImmutablePair< String, Long >( ID, existingEntity.getId() );
		final List< T > searchResults = getService().search( nameConstraint, idConstraint );
		
		// Then
		assertThat( searchResults, not( hasItem( existingEntity ) ) );
	}
	
	// search - by negated id,name
	
	@Test
	public final void whenSearchByNegatedNameIsPerformed_thenResultsAreFound(){
		final T existingEntity1 = getService().create( createNewEntity() );
		final T existingEntity2 = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( "~" + NAME, existingEntity1.getName() );
		final List< T > searchResults = getService().search( nameConstraint );
		
		// Then
		assertThat( searchResults, not( hasItem( existingEntity1 ) ) );
		assertThat( searchResults, hasItem( existingEntity2 ) );
	}
	
	@Test
	public final void whenSearchByNegatedIdIsPerformed_thenResultsAreFound(){
		final T existingEntity1 = getService().create( createNewEntity() );
		final T existingEntity2 = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, Long > idConstraint = new ImmutablePair< String, Long >( "~" + ID, existingEntity1.getId() );
		final List< T > searchResults = getService().search( idConstraint );
		
		// Then
		assertThat( searchResults, not( hasItem( existingEntity1 ) ) );
		assertThat( searchResults, hasItem( existingEntity2 ) );
	}
	
	// template method
	
	protected abstract IService< T > getService();
	
	protected abstract T createNewEntity();
	
}
