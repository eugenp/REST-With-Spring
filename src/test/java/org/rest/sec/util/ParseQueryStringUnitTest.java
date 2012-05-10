package org.rest.sec.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.rest.util.SearchCommonUtil.ID;
import static org.rest.util.SearchCommonUtil.NAME;
import static org.rest.util.SearchCommonUtil.NEGATION;
import static org.rest.util.SearchCommonUtil.OP;
import static org.rest.util.SearchCommonUtil.SEPARATOR;
import static org.rest.util.SearchCommonUtil.parseQueryString;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.Test;
import org.rest.common.ClientOperation;

public final class ParseQueryStringUnitTest{
	
	// single key-value tuple
	
	// -- incorrect
	
	@Test( expected = NullPointerException.class )
	public final void whenNullQueryIsParsed_thenException(){
		parseQueryString( null );
	}
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryDoesNotContainConstraintField_whenQueryIsParsed_thenException(){
		parseQueryString( "justtext" );
	}
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainIdConstraintFieldButNoValue_whenQueryIsParsed_thenException(){
		parseQueryString( ID + OP );
	}
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainIdConstraintNonNumericalValueForId_whenQueryIsParsed_thenException(){
		parseQueryString( ID + OP + "aa" );
	}
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainSingleIdConstraintWithValidFieldValueAndAdditionalInvalidCharacter_whenQueryIsParsed_thenException(){
		parseQueryString( ID + OP + "aa," );
	}
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainSingleIdConstraintWithCorrectFieldValueButInvalidSeparator_whenQueryIsParsed_thenException(){
		parseQueryString( "id|aa," );
	}
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainIdConstraintWithUnknownKey_whenQueryIsParsed_thenException(){
		parseQueryString( "somekey|aa" );
	}
	
	// -- correct
	
	@Test
	public final void givenQueryContainsValidIdConstraint_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( ID + OP + "2" );
	}
	
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultIsNotNull(){
		final List< ImmutableTriple< String, ClientOperation, ? >> queryTyples = parseQueryString( ID + OP + "2" );
		
		assertFalse( queryTyples.isEmpty() );
	}
	
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultKeyIsCorrect(){
		final List< ImmutableTriple< String, ClientOperation, ? >> queryTyples = parseQueryString( ID + OP + "2" );
		final ImmutableTriple< String, ClientOperation, ? > pair = queryTyples.get( 0 );
		assertEquals( ID, pair.getLeft() );
	}
	
	@Test
	public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultValueIsCorrect(){
		final List< ImmutableTriple< String, ClientOperation, ? >> queryTyples = parseQueryString( ID + OP + "2" );
		final ImmutableTriple< String, ClientOperation, ? > pair = queryTyples.get( 0 );
		assertEquals( 2l, pair.getRight() );
	}
	
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( NAME + OP + "some" );
	}
	
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultIsNotNull(){
		final List< ImmutableTriple< String, ClientOperation, ? >> queryTyples = parseQueryString( NAME + OP + "some" );
		
		assertFalse( queryTyples.isEmpty() );
	}
	
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultKeyIsCorrect(){
		final List< ImmutableTriple< String, ClientOperation, ? >> queryTyples = parseQueryString( NAME + OP + "some" );
		final ImmutableTriple< String, ClientOperation, ? > pair = queryTyples.get( 0 );
		assertEquals( NAME, pair.getLeft() );
	}
	
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultValueIsCorrect(){
		final List< ImmutableTriple< String, ClientOperation, ? >> queryTyples = parseQueryString( NAME + OP + "some" );
		final ImmutableTriple< String, ClientOperation, ? > pair = queryTyples.get( 0 );
		assertEquals( "some", pair.getRight() );
	}
	
	@Test
	public final void givenValidQueryWithNameConstraint_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect(){
		final List< ImmutableTriple< String, ClientOperation, ? >> queryTyples = parseQueryString( NAME + OP + "Some" );
		final ImmutableTriple< String, ClientOperation, ? > pair = queryTyples.get( 0 );
		assertEquals( "Some", pair.getRight() );
	}
	
	// -- correct - with negation
	
	@Test
	public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( ID + NEGATION + OP + "2" );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( NAME + NEGATION + OP + "some" );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedIdAndNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( ID + NEGATION + OP + "3" + SEPARATOR + NAME + NEGATION + OP + "some" );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( ID + NEGATION + OP + "3" + SEPARATOR + NAME + OP + "some" );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenResultsAreCorrect(){
		final List< ImmutableTriple< String, ClientOperation, ? >> parsedQueryString = parseQueryString( ID + NEGATION + OP + "3" + SEPARATOR + NAME + OP + "some" );
		
		assertEquals( 3l, parsedQueryString.get( 0 ).getRight() );
		assertEquals( "some", parsedQueryString.get( 1 ).getRight() );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenResultTypesAreCorrect(){
		final String name = randomAlphabetic( 8 );
		final List< ImmutableTriple< String, ClientOperation, ? >> parseQueryString = parseQueryString( NAME + NEGATION + OP + name );
		assertTrue( parseQueryString.get( 0 ).getRight().getClass().equals( String.class ) );
		assertTrue( parseQueryString.get( 0 ).getRight().equals( name ) );
		assertTrue( parseQueryString.get( 0 ).getLeft().equals( NAME ) );
	}
	
	@Test
	public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenResultTypesAreCorrect(){
		final List< ImmutableTriple< String, ClientOperation, ? >> parseQueryString = parseQueryString( ID + NEGATION + OP + "2" );
		assertTrue( parseQueryString.get( 0 ).getRight().getClass().equals( Long.class ) );
		assertTrue( parseQueryString.get( 0 ).getRight().equals( 2l ) );
		assertTrue( parseQueryString.get( 0 ).getLeft().equals( ID ) );
	}
	
	// multiple key-value tuples
	
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainOneValidConstraintAndOneIncorrectOne_whenQueryIsParsed_thenException(){
		parseQueryString( ID + OP + "2" + SEPARATOR + "name" );
	}
	
	@Test
	public final void givenQueryContainANameConstraintWithNumbersInTheName_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( ID + OP + "2" + SEPARATOR + NAME + OP + "eugen2" );
	}
	
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenNoExceptions(){
		parseQueryString( ID + OP + "2" + SEPARATOR + NAME + OP + "eugen" );
	}
	
	@Test
	public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final String queryString = ID + OP + "2" + SEPARATOR + NAME + OP + "eugen";
		final List< ImmutableTriple< String, ClientOperation, ? >> parsedQueryString = parseQueryString( queryString );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( ID, ClientOperation.EQ, 2l ) ) );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( NAME, ClientOperation.EQ, "eugen" ) ) );
	}
	
	// multiple key-value tuples (for the same key): ex: id=2,id=3
	
	@Test
	public final void givenQueryContainTwoValidConstraintsForTheIdKey_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final String queryString = ID + OP + "2" + SEPARATOR + ID + OP + "3";
		final List< ImmutableTriple< String, ClientOperation, ? >> parsedQueryString = parseQueryString( queryString );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( ID, ClientOperation.EQ, 2l ) ) );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( ID, ClientOperation.EQ, 3l ) ) );
	}
	
	@Test
	public final void givenQueryContainTwoValidConstraintsForTheNameKey_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final String queryString = NAME + OP + "me" + SEPARATOR + NAME + OP + "andyou";
		final List< ImmutableTriple< String, ClientOperation, ? >> parsedQueryString = parseQueryString( queryString );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( NAME, ClientOperation.EQ, "me" ) ) );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( NAME, ClientOperation.EQ, "andyou" ) ) );
	}
	
	/**
	 * - note: the order of constraints in the query language should be alphabetic (id then name)
	 */
	@Test( expected = IllegalStateException.class )
	public final void givenQueryContainTwoValidNameConstraintsAndTwoValidIdConstraintsInTheIncorrectOrder_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final String queryString = NAME + OP + "me" + SEPARATOR + NAME + OP + "andyou" + SEPARATOR + ID + OP + "2" + SEPARATOR + ID + OP + "3";
		parseQueryString( queryString );
	}
	
	@Test
	public final void givenQueryContainTwoValidIdConstraintsAndTwoValidNameConstraints_whenQueryIsParsed_thenConstraintsAreCorrect(){
		final String queryString = ID + OP + "2" + SEPARATOR + ID + OP + "3" + SEPARATOR + NAME + OP + "me" + SEPARATOR + NAME + OP + "andyou";
		final List< ImmutableTriple< String, ClientOperation, ? >> parsedQueryString = parseQueryString( queryString );
		
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( ID, ClientOperation.EQ, 2l ) ) );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( ID, ClientOperation.EQ, 3l ) ) );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( NAME, ClientOperation.EQ, "me" ) ) );
		assertThat( parsedQueryString, hasItem( createNewImmutableTriple( NAME, ClientOperation.EQ, "andyou" ) ) );
	}
	
	//
	
	ImmutableTriple< String, ClientOperation, Long > createNewImmutableTriple( final String key, final ClientOperation op, final Long value ){
		return new ImmutableTriple< String, ClientOperation, Long >( key, op, value );
	}
	
	ImmutableTriple< String, ClientOperation, String > createNewImmutableTriple( final String key, final ClientOperation op, final String value ){
		return new ImmutableTriple< String, ClientOperation, String >( key, op, value );
	}
	
}
