package org.rest.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class SearchCommonUtil{
	
	public static final String Q_PARAM = "q";
	
	public static final String SEPARATOR = ",";
	public static final String OP = "=";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String NEGATION = "~";
	
	private SearchCommonUtil(){
		throw new UnsupportedOperationException();
	}
	
	//
	public static List< ImmutablePair< String, ? >> parseQueryString( final String queryString ){
		Preconditions.checkNotNull( queryString );
		Preconditions.checkState( queryString.matches( "(id~?=[0-9]+)?,?(name~?=[0-9a-zA-Z]+)?" ) );
		
		final List< ImmutablePair< String, ? >> tuplesList = Lists.newArrayList();
		final String[] tuples = queryString.split( SEPARATOR );
		for( final String tuple : tuples ){
			final String[] keyAndValue = tuple.split( NEGATION + "?" + OP );
			Preconditions.checkState( keyAndValue.length == 2 );
			tuplesList.add( constructTuple( keyAndValue[0], keyAndValue[1] ) );
		}
		
		return tuplesList;
	}
	
	private static ImmutablePair< String, ? > constructTuple( final String key, final String value ){
		if( key.endsWith( ID ) ){
			return new ImmutablePair< String, Long >( key, Long.parseLong( value ) );
		}
		return new ImmutablePair< String, String >( key, value );
	}
	
}
