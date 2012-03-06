package org.rest.sec.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class SearchUtil{
	
	private SearchUtil(){
		throw new UnsupportedOperationException();
	}
	
	//
	
	public static List< ImmutablePair< String, String >> parseQueryString( final String queryString ){
		Preconditions.checkNotNull( queryString );
		Preconditions.checkState( queryString.matches( "(id:[0-9]+),?(name:[0-9a-z]+)?" ) );
		
		final List< ImmutablePair< String, String >> tuplesList = Lists.newArrayList();
		final String[] tuples = queryString.split( "," );
		for( final String tuple : tuples ){
			final String[] keyAndValue = tuple.split( ":" );
			Preconditions.checkState( keyAndValue.length == 2 );
			tuplesList.add( new ImmutablePair< String, String >( keyAndValue[0], keyAndValue[1] ) );
		}
		
		return tuplesList;
	}
	
}
