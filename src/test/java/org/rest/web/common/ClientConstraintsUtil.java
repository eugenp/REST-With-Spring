package org.rest.web.common;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.rest.common.ClientOperation;

public class ClientConstraintsUtil{
	
	private ClientConstraintsUtil(){
		throw new AssertionError();
	}
	
	//
	
	public static ImmutablePair< String, ClientOperation > createNameConstraint( final String name ){
		return new ImmutablePair< String, ClientOperation >( name, ClientOperation.EQ );
	}
	
	public static ImmutablePair< String, ClientOperation > createNegatedNameConstraint( final String name ){
		return new ImmutablePair< String, ClientOperation >( name, ClientOperation.NEG_EQ );
	}
	
	public static Pair< Long, ClientOperation > createIdConstraint( final Long id ){
		return new ImmutablePair< Long, ClientOperation >( id, ClientOperation.EQ );
	}
	
	public static ImmutablePair< Long, ClientOperation > createNegatedIdConstraint( final Long id ){
		return new ImmutablePair< Long, ClientOperation >( id, ClientOperation.NEG_EQ );
	}

}
