package org.rest.sec.util;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;

public final class ParseQueryStringUnitTest{
	
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
	public final void givenQueryContainIdConstraintFieldButNoValue_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainIdConstraintNonNumericalValueForId_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "aa" );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainSingleIdConstraintWithValidFieldValueAndAdditionalInvalidCharacter_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "aa," );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainSingleIdConstraintWithCorrectFieldValueButInvalidSeparator_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "id|aa," );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainIdConstraintWithUnknownKey_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( "somekey|aa" );
	}
	
	// -- correct
	
	@Test
	public final void givenQueryContainsValidIdConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2" );
	}
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultIsNotNull(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2" );
		
		assertFalse( queryTyples.isEmpty() );
	}
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultKeyIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( SearchUtil.ID, pair.getLeft() );
	}
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultValueIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( 2l, pair.getRight() );
	}
	
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.DELIMITER + "some" );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultIsNotNull(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.DELIMITER + "some" );
		
		assertFalse( queryTyples.isEmpty() );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultKeyIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.DELIMITER + "some" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( SearchUtil.NAME, pair.getLeft() );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultValueIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.DELIMITER + "some" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( "some", pair.getRight() );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.DELIMITER + "Some" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( "Some", pair.getRight() );
	}
	
	// -- correct - with negation
	
	@Test
	public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( "~id" + SearchUtil.DELIMITER + "2" );
	}
	@Test
	public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( "~name" + SearchUtil.DELIMITER + "some" );
	}
	@Test
	public final void givenQueryContainsValidNegatedIdAndNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( "~id" + SearchUtil.DELIMITER + "3,~name" + SearchUtil.DELIMITER + "some" );
	}
	@Test
	public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( "~id" + SearchUtil.DELIMITER + "3,name" + SearchUtil.DELIMITER + "some" );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenResultsAreCorrect(){
		final List< ImmutablePair< String, ? >> parsedQueryString = SearchUtil.parseQueryString( "~id" + SearchUtil.DELIMITER + "3,name" + SearchUtil.DELIMITER + "some" );
		
		assertEquals( 3l, parsedQueryString.get( 0 ).getRight() );
		assertEquals( "some", parsedQueryString.get( 1 ).getRight() );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenResultTypesAreCorrect(){
		final List< ImmutablePair< String, ? >> parseQueryString = SearchUtil.parseQueryString( "~name" + SearchUtil.DELIMITER + "some" );
		assertTrue( parseQueryString.get( 0 ).getRight().getClass().equals( String.class ) );
	}
	@Test
	public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenResultTypesAreCorrect(){
		final List< ImmutablePair< String, ? >> parseQueryString = SearchUtil.parseQueryString( "~id" + SearchUtil.DELIMITER + "2" );
		assertTrue( parseQueryString.get( 0 ).getRight().getClass().equals( Long.class ) );
	}
	
	// multiple key-value tuples
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainOneValidConstraintAndOneIncorrectOne_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2,name" );
	}
	
	@Test
	public final void givenQueryContainANameConstraintWithNumbersInTheName_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2,name" + SearchUtil.DELIMITER + "eugen2" );
	}
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2,name" + SearchUtil.DELIMITER + "eugen" );
	}
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final List< ImmutablePair< String, ? >> parsedQueryString = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.DELIMITER + "2,name" + SearchUtil.DELIMITER + "eugen" );
		assertThat( parsedQueryString, hasItem( new ImmutablePair< String, Long >( SearchUtil.ID, 2l ) ) );
		assertThat( parsedQueryString, hasItem( new ImmutablePair< String, String >( SearchUtil.NAME, "eugen" ) ) );
	}
	
}
