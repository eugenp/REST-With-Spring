package org.rest.sec.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class ConstructQueryStringUnitTest{
	
	// not considering negation
	
	@Test
	public final void whenQueryStringIsConstructedFromNull_thenNoException(){
		SearchUtil.constructQueryString( (Long) null, null );
	}
	
	@Test
	public final void whenQueryStringIsConstructedFromId_thenNoException(){
		SearchUtil.constructQueryString( 2l, null );
	}
	@Test
	public final void whenQueryStringIsConstructedFromName_thenNoException(){
		SearchUtil.constructQueryString( null, "someName" );
	}
	
	@Test
	public final void whenQueryStringIsConstructedFromId_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( 2l, null );
		assertEquals( SearchUtil.ID + SearchUtil.DELIMITER + "2", queryString );
	}
	@Test
	public final void whenQueryStringIsConstructedFromName_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( null, "someName" );
		assertEquals( SearchUtil.NAME + SearchUtil.DELIMITER + "someName", queryString );
	}
	
	@Test
	public final void whenQueryStringIsConstructedFromIdAndName_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( 32l, "someName" );
		assertEquals( SearchUtil.ID + SearchUtil.DELIMITER + "32,name" + SearchUtil.DELIMITER + "someName", queryString );
	}
	
	// considering negation
	
	@Test
	public final void whenQueryStringIsConstructedFromNegatedId_thenNoException(){
		SearchUtil.constructQueryString( 2l, true, null, false );
	}
	@Test
	public final void whenQueryStringIsConstructedFromNegatedId_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( 2l, true, null, false );
		assertEquals( SearchUtil.NEGATION + SearchUtil.ID + SearchUtil.DELIMITER + "2", queryString );
	}
	
	@Test
	public final void whenQueryStringIsConstructedFromNegatedName_thenNoException(){
		SearchUtil.constructQueryString( null, false, "some", true );
	}
	@Test
	public final void whenQueryStringIsConstructedFromNegatedName_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( null, false, "some", true );
		assertEquals( SearchUtil.NEGATION + SearchUtil.NAME + SearchUtil.DELIMITER + "some", queryString );
	}
	
	@Test
	public final void whenQueryStringIsConstructedFromIdAndNegatedName_thenNoException(){
		SearchUtil.constructQueryString( 2l, false, "some", true );
	}
	@Test
	public final void whenQueryStringIsConstructedFromIdAndNegatedName_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( 2l, false, "some", true );
		assertEquals( SearchUtil.ID + SearchUtil.DELIMITER + "2," + SearchUtil.NEGATION + SearchUtil.NAME + SearchUtil.DELIMITER + "some", queryString );
	}
	@Test
	public final void whenQueryStringIsConstructedFromNameAndNegatedId_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( 2l, true, "some", false );
		assertEquals( SearchUtil.NEGATION + SearchUtil.ID + SearchUtil.DELIMITER + "2," + SearchUtil.NAME + SearchUtil.DELIMITER + "some", queryString );
	}
	@Test
	public final void whenQueryStringIsConstructedFromNegatedNameAndNegatedId_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( 2l, true, "some", true );
		assertEquals( SearchUtil.NEGATION + SearchUtil.ID + SearchUtil.DELIMITER + "2," + SearchUtil.NEGATION + SearchUtil.NAME + SearchUtil.DELIMITER + "some", queryString );
	}
	
}
