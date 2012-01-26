package org.rest.testing.marshaller;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component( "jacksonMarshaller" )
public final class JacksonMarshaller implements IMarshaller{
	private static final Logger logger = LoggerFactory.getLogger( JacksonMarshaller.class );

	ObjectMapper objectMapper;
	
	public JacksonMarshaller(){
		super();
		
		objectMapper = new ObjectMapper();
	}
	
	// API
	
	@Override
	public final < T >String encode( final T entity ){
		Preconditions.checkNotNull( entity );
		String entityAsJSON = null;
		try{
			entityAsJSON = objectMapper.writeValueAsString( entity );
		}
		catch( final Exception ex ){
			logger.error( "", ex );
		}
		
		return entityAsJSON;
	}

	@Override
	public final < T >T decode( final String entityAsString, final Class< T > clazz ){
		Preconditions.checkNotNull( entityAsString );
		
		T entity = null;
		try{
			entity = objectMapper.readValue( entityAsString, clazz );
		}
		catch( final JsonParseException e ){
			e.printStackTrace();
		}
		catch( final JsonMappingException e ){
			e.printStackTrace();
		}
		catch( final IOException e ){
			e.printStackTrace();
		}
		
		return entity;
	}
	
	@Override
	public final String getMime(){
		return MediaType.APPLICATION_JSON.toString();
	}
	
}
