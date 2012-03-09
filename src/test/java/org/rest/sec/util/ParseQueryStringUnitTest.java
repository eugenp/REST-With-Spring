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
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainIdConstraintNonNumericalValueForId_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "aa" );
	}
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainSingleIdConstraintWithValidFieldValueAndAdditionalInvalidCharacter_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "aa," );
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
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" );
	}
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultIsNotNull(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" );
		
		assertFalse( queryTyples.isEmpty() );
	}
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultKeyIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( SearchUtil.ID, pair.getLeft() );
	}
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultValueIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( 2l, pair.getRight() );
	}
	
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.OP + "some" );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultIsNotNull(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.OP + "some" );
		
		assertFalse( queryTyples.isEmpty() );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultKeyIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.OP + "some" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( SearchUtil.NAME, pair.getLeft() );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultValueIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.OP + "some" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( "some", pair.getRight() );
	}
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect(){
		final List< ImmutablePair< String, ? >> queryTyples = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.OP + "Some" );
		final ImmutablePair< String, ? > pair = queryTyples.get( 0 );
		assertEquals( "Some", pair.getRight() );
	}
	
	// -- correct - with negation
	
	@Test
	public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.NEGATION + SearchUtil.OP + "2" );
	}
	@Test
	public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.NEGATION + SearchUtil.OP + "some" );
	}
	@Test
	public final void givenQueryContainsValidNegatedIdAndNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.NEGATION + SearchUtil.OP + "3" + SearchUtil.SEPARATOR + SearchUtil.NAME + SearchUtil.NEGATION + SearchUtil.OP + "some" );
	}
	@Test
	public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.NEGATION + SearchUtil.OP + "3" + SearchUtil.SEPARATOR + SearchUtil.NAME + SearchUtil.OP + "some" );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenResultsAreCorrect(){
		final List< ImmutablePair< String, ? >> parsedQueryString = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.NEGATION + SearchUtil.OP + "3" + SearchUtil.SEPARATOR + SearchUtil.NAME + SearchUtil.OP + "some" );
		
		assertEquals( 3l, parsedQueryString.get( 0 ).getRight() );
		assertEquals( "some", parsedQueryString.get( 1 ).getRight() );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenResultTypesAreCorrect(){
		final List< ImmutablePair< String, ? >> parseQueryString = SearchUtil.parseQueryString( SearchUtil.NAME + SearchUtil.NEGATION + SearchUtil.OP + "some" );
		assertTrue( parseQueryString.get( 0 ).getRight().getClass().equals( String.class ) );
	}
	@Test
	public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenResultTypesAreCorrect(){
		final List< ImmutablePair< String, ? >> parseQueryString = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.NEGATION + SearchUtil.OP + "2" );
		assertTrue( parseQueryString.get( 0 ).getRight().getClass().equals( Long.class ) );
	}
	
	// multiple key-value tuples
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainOneValidConstraintAndOneIncorrectOne_whenQueryIsParsed_thenException(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" + SearchUtil.SEPARATOR + "name" );
	}
	
	@Test
	public final void givenQueryContainANameConstraintWithNumbersInTheName_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" + SearchUtil.SEPARATOR + SearchUtil.NAME + SearchUtil.OP + "eugen2" );
	}
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenNoExceptions(){
		SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" + SearchUtil.SEPARATOR + SearchUtil.NAME + SearchUtil.OP + "eugen" );
	}
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final List< ImmutablePair< String, ? >> parsedQueryString = SearchUtil.parseQueryString( SearchUtil.ID + SearchUtil.OP + "2" + SearchUtil.SEPARATOR + SearchUtil.NAME + SearchUtil.OP + "eugen" );
		assertThat( parsedQueryString, hasItem( new ImmutablePair< String, Long >( SearchUtil.ID, 2l ) ) );
		assertThat( parsedQueryString, hasItem( new ImmutablePair< String, String >( SearchUtil.NAME, "eugen" ) ) );
	}
	
}
