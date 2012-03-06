package org.rest.sec.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class ConstructQueryStringUnitTest{
	
	//
	
	@Test
	public final void whenQueryStringIsConstructedFromNull_thenNoException(){
		SearchUtil.constructQueryString( null, null );
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
		assertEquals( "id:2", queryString );
	}
	@Test
	public final void whenQueryStringIsConstructedFromName_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( null, "someName" );
		assertEquals( "name:someName", queryString );
	}
	
	@Test
	public final void whenQueryStringIsConstructedFromIdAndName_thenQueryStringIsCorrect(){
		final String queryString = SearchUtil.constructQueryString( 32l, "someName" );
		assertEquals( "id:32,name:someName", queryString );
	}

}
