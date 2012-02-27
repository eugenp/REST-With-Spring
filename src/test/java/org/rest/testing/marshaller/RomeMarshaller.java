package org.rest.testing.marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component( "romeMarshaller" )
public final class RomeMarshaller implements IMarshaller{
	private static final Logger logger = LoggerFactory.getLogger( RomeMarshaller.class );
	
	public RomeMarshaller(){
		super();
	}
	
	// API
	
	@Override
	public final < T >String encode( final T entity ){
		Preconditions.checkNotNull( entity );
		
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final < T >T decode( final String entityAsString, final Class< T > clazz ){
		Preconditions.checkNotNull( entityAsString );
		
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final String getMime(){
		return MediaType.APPLICATION_ATOM_XML.toString();
	}
	
}
