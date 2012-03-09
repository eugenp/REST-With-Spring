package org.rest.sec.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.rest.client.template.impl.ClientOperations;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class SearchUtil{
	
	public static final String SEPARATOR = ",";
	public static final String DELIMITER = "=";
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String NEGATION = "~";
	
	private SearchUtil(){
		throw new UnsupportedOperationException();
	}
	
	//
	
	public static List< ImmutablePair< String, ? >> parseQueryString( final String queryString ){
		Preconditions.checkNotNull( queryString );
		Preconditions.checkState( queryString.matches( "(~?id=[0-9]+)?,?(~?name=[0-9a-zA-Z]+)?" ) );
		
		final List< ImmutablePair< String, ? >> tuplesList = Lists.newArrayList();
		final String[] tuples = queryString.split( SEPARATOR );
		for( final String tuple : tuples ){
			final String[] keyAndValue = tuple.split( DELIMITER );
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
	
	//
	
	public static String constructQueryString( final Long id, final String name ){
		return constructQueryString( id, false, name, false );
	}
	
	public static String constructQueryString( final Pair< Long, ClientOperations > idOp, final Pair< String, ClientOperations > nameOp ){
		final Long id = ( idOp == null ) ? null : idOp.getLeft();
		final boolean negatedId = ( idOp == null ) ? false : idOp.getRight().isNegated();
		final String name = ( nameOp == null ) ? null : nameOp.getLeft();
		final boolean negatedName = ( nameOp == null ) ? false : nameOp.getRight().isNegated();
		return constructQueryString( id, negatedId, name, negatedName );
	}
	static String constructQueryString( final Long id, final boolean negatedId, final String name, final boolean negatedName ){
		final StringBuffer queryString = new StringBuffer();
		String key = null;
		if( id != null ){
			key = ( negatedId ) ? NEGATION + ID : ID;
			queryString.append( key + DELIMITER + id );
		}
		if( name != null ){
			if( queryString.length() != 0 ){
				queryString.append( SEPARATOR );
			}
			key = ( negatedName ) ? NEGATION + NAME : NAME;
			queryString.append( key + DELIMITER + name );
		}
		
		return queryString.toString();
	}
	
}
