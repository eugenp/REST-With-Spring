package org.rest.web.common;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.rest.client.template.IEntityOperations;
import org.rest.client.template.IRESTTemplate;
import org.rest.common.ClientOperation;
import org.rest.common.INameableEntity;
import org.rest.util.IDUtils;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractSearchRESTIntegrationTest< T extends INameableEntity >{
	
	public AbstractSearchRESTIntegrationTest(){
		super();
	}
	
	// tests
	
	// search - by id
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedById_thenNoExceptions(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );

		getTemplate().searchAsResponse( createIdConstraint( existingResource.getId() ), (Pair< String, ClientOperation >) null );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedById_then200IsReceived(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final Response searchResponse = getTemplate().searchAsResponse( createIdConstraint( existingResource.getId() ), null );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByIdAndUnmarshalled_thenNoException(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().search( createIdConstraint( existingResource.getId() ), null );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByIdAndUnmarshalled_thenResourceIsFound(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( createIdConstraint( existingResource.getId() ), null );
		
		// Then
		assertThat( found, hasItem( existingResource ) );
	}
	
	// search - by name
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByName_thenNoExceptions(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().searchAsResponse( null, createNameConstraint( existingResource.getName() ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByName_then200IsReceived(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final Response searchResponse = getTemplate().searchAsResponse( null, createNameConstraint( existingResource.getName() ) );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNameAndUnmarshalled_thenNoException(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().search( null, createNameConstraint( existingResource.getName() ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNameAndUnmarshalled_thenResourceIsFound(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( null, createNameConstraint( existingResource.getName() ) );
		
		// Then
		assertThat( found, hasItem( existingResource ) );
	}
	
	// search - by id and name
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByIdAndName_then200IsReceived(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final Response searchResponse = getTemplate().searchAsResponse( createIdConstraint( existingResource.getId() ), createNameConstraint( existingResource.getName() ) );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByIdAndNameAndUnmarshalled_thenResourceIsFound(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( createIdConstraint( existingResource.getId() ), createNameConstraint( existingResource.getName() ) );
		
		// Then
		assertThat( found, hasItem( existingResource ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByIdAndWrongNameAndUnmarshalled_thenResourceIsFound(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( createIdConstraint( existingResource.getId() ), createNameConstraint( randomAlphabetic( 8 ) ) );
		
		// Then
		assertThat( found, not( hasItem( existingResource ) ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByWrongIdAndCorrectNameAndUnmarshalled_thenResourceIsFound(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( createIdConstraint( IDUtils.randomPositiveLong() ), createNameConstraint( existingResource.getName() ) );
		
		// Then
		assertThat( found, not( hasItem( existingResource ) ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByWrongIdAndWrongNameAndUnmarshalled_thenResourceIsFound(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( createIdConstraint( IDUtils.randomPositiveLong() ), createNameConstraint( randomAlphabetic( 8 ) ) );
		
		// Then
		assertThat( found, not( hasItem( existingResource ) ) );
	}
	
	// search - by negated id,name
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNegatedName_then200IsReceived(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		final ImmutablePair< String, ClientOperation > negatedNameConstraint = new ImmutablePair< String, ClientOperation >( existingResource.getName(), ClientOperation.NEG_EQ );
		
		// When
		final Response searchResponse = getTemplate().searchAsResponse( null, negatedNameConstraint );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNegatedId_then200IsReceived(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		final ImmutablePair< Long, ClientOperation > negatedIdConstraint = new ImmutablePair< Long, ClientOperation >( existingResource.getId(), ClientOperation.NEG_EQ );
		
		// When
		final Response searchResponse = getTemplate().searchAsResponse( negatedIdConstraint, null );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNegatedId_thenResourceIsNotFound(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( createNegatedIdConstraint( existingResource.getId() ), null );
		
		// Then
		assertThat( found, not( hasItem( existingResource ) ) );
	}
	
	@Test
	public final void givenResourcesExists_whenResourceIfSearchedByNegatedId_thenTheOtherResourcesAreFound(){
		final T existingResource1 = getTemplate().create( getTemplate().createNewEntity() );
		final T existingResource2 = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< T > found = getTemplate().search( createNegatedIdConstraint( existingResource1.getId() ), null );
		
		// Then
		assertThat( found, hasItem( existingResource2 ) );
	}
	
	// search - with paging
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNameWithPaging_then200IsReceived(){
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final ImmutablePair< String, ClientOperation > nameConstraint = new ImmutablePair< String, ClientOperation >( existingResource.getName(), ClientOperation.EQ );
		final Response searchResponse = getTemplate().searchAsResponse( null, nameConstraint, 0, 2 );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourcesExists_whenResourceIfSearchedByNameWithPagingOfSize2_thenMax2ResultsAreReceived(){
		final T existingResource1 = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final ImmutablePair< String, ClientOperation > nameConstraint = new ImmutablePair< String, ClientOperation >( existingResource1.getName(), ClientOperation.NEG_EQ );
		final List< T > searchResults = getTemplate().search( null, nameConstraint, 0, 2 );
		
		// Then
		assertThat( searchResults.size(), is( 2 ) );
	}
	
	// template
	
	protected abstract IRESTTemplate< T > getTemplate();
	
	protected abstract IEntityOperations< T > getEntityOperations();
	
	protected final RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
	// util
	
	final ImmutablePair< String, ClientOperation > createNameConstraint( final String name ){
		return new ImmutablePair< String, ClientOperation >( name, ClientOperation.EQ );
	}
	
	final ImmutablePair< String, ClientOperation > createNegatedNameConstraint( final String name ){
		return new ImmutablePair< String, ClientOperation >( name, ClientOperation.NEG_EQ );
	}
	
	final Pair< Long, ClientOperation > createIdConstraint( final Long id ){
		return new ImmutablePair< Long, ClientOperation >( id, ClientOperation.EQ );
	}
	
	final ImmutablePair< Long, ClientOperation > createNegatedIdConstraint( final Long id ){
		return new ImmutablePair< Long, ClientOperation >( id, ClientOperation.NEG_EQ );
	}
	
}
