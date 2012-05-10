package org.rest.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.rest.common.ClientOperation;

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
	
	public static List< ImmutableTriple< String, ClientOperation, ? >> parseQueryString( final String queryString ){
		Preconditions.checkNotNull( queryString );
		Preconditions.checkState( queryString.matches( "((id~?=[0-9]+)?,?)*((name~?=[0-9a-zA-Z]+),?)*" ) );
		
		final List< ImmutableTriple< String, ClientOperation, ? >> tuplesList = Lists.newArrayList();
		final String[] tuples = queryString.split( SEPARATOR );
		for( final String tuple : tuples ){
			final String[] keyAndValue = tuple.split( OP );
			Preconditions.checkState( keyAndValue.length == 2 );
			tuplesList.add( constructTriple( keyAndValue[0], keyAndValue[1] ) );
		}
		
		return tuplesList;
	}
	
	public static ImmutableTriple< String, ClientOperation, ? > constructTriple( final String key, final String value ){
		boolean negated = false;
		if( key.endsWith( NEGATION ) ){
			negated = true;
		}
		
		if( key.startsWith( ID ) ){
			if( negated ){
				return new ImmutableTriple< String, ClientOperation, Long >( key.substring( 0, key.length() - 1 ), ClientOperation.NEG_EQ, Long.parseLong( value ) );
			}
			return new ImmutableTriple< String, ClientOperation, Long >( key, ClientOperation.EQ, Long.parseLong( value ) );
		}
		else if( key.startsWith( NAME ) ){
			if( negated ){
				return new ImmutableTriple< String, ClientOperation, String >( key.substring( 0, key.length() - 1 ), ClientOperation.NEG_EQ, value );
			}
			return new ImmutableTriple< String, ClientOperation, String >( key, ClientOperation.EQ, value );
		}
		
		return null;
	}
	
	private static ImmutablePair< String, ? > constructTuple( final String key, final String value ){
		if( key.endsWith( ID ) ){
			return new ImmutablePair< String, Long >( key, Long.parseLong( value ) );
		}
		return new ImmutablePair< String, String >( key, value );
	}
	
}
