package org.rest.sec.util;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;

public final class SearchUtilUnitTest{
	
	// single key-value tuple
	
	// -- incorrect
	
	@Test( expected = NullPointerException.class )
	public final void whenNullQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( null );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryDoesNotContainConstraintField_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "justtext" );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainConstraintFieldButNoValue_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "id:" );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainConstraintNonNumericalValueForId_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "id:aa" );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainSingleConstraintWithValidFieldValueAndAdditionalInvalidCharacter_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "id:aa," );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainSingleConstraintWithCorrectFieldValueButInvalidSeparator_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "id|aa," );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainConstraintWithUnknownKey_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "somekey|aa" );
	}
	
	// -- correct
	
	@Test
	public final void givenQueryContainConstraintValidFieldValueForId_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( "id:2" );
	}
	@Test
	public final void givenQueryStringValid_whenQueryIsParsed_thenResultIsNotNull(){
		final List< ImmutablePair< String, String >> queryTyples = SearchUtil.parseQueryString( "id:2" );
		
		assertFalse( queryTyples.isEmpty() );
	}
	@Test
	public final void givenQueryStringValid_whenQueryIsParsed_thenResultKeyIsCorrect(){
		final List< ImmutablePair< String, String >> queryTyples = SearchUtil.parseQueryString( "id:2" );
		final ImmutablePair< String, String > pair = queryTyples.get( 0 );
		assertEquals( "id", pair.getLeft() );
	}
	@Test
	public final void givenQueryStringValid_whenQueryIsParsed_thenResultValueIsCorrect(){
		final List< ImmutablePair< String, String >> queryTyples = SearchUtil.parseQueryString( "id:2" );
		final ImmutablePair< String, String > pair = queryTyples.get( 0 );
		assertEquals( "2", pair.getRight() );
	}
	
	// multiple key-value tuples
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainOneValidConstraintAndOneIncorrectOne_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "id:2,name" );
	}
	
	@Test
	public final void givenQueryContainANameConstraintWithNumbersInTheName_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( "id:2,name:eugen2" );
	}
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( "id:2,name:eugen" );
	}
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final List< ImmutablePair< String, String >> parsedQueryString = SearchUtil.parseQueryString( "id:2,name:eugen" );
		assertThat( parsedQueryString, hasItem( new ImmutablePair< String, String >( "id", "2" ) ) );
		assertThat( parsedQueryString, hasItem( new ImmutablePair< String, String >( "name", "eugen" ) ) );
	}
	
}
