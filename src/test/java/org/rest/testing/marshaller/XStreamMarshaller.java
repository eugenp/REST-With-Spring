package org.rest.testing.marshaller;

import org.rest.poc.model.Bar;
import org.rest.poc.model.Foo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.thoughtworks.xstream.XStream;

@Component( "xstreamMarshaller" )
public final class XStreamMarshaller implements IMarshaller{
	
	private XStream xstream;
	
	public XStreamMarshaller(){
		super();

		xstream = new XStream();
		xstream.autodetectAnnotations( true );
		xstream.processAnnotations( Foo.class );
		xstream.processAnnotations( Bar.class );
	}
	
	// API
	
	@Override
	public final < T >String encode( final T entity ){
		Preconditions.checkNotNull( entity );
		return xstream.toXML( entity );
	}
	@SuppressWarnings( "unchecked" )
	@Override
	public final < T >T decode( final String entityAsXML, final Class< T > clazz ){
		Preconditions.checkNotNull( entityAsXML );
		return (T) xstream.fromXML( entityAsXML );
	}
	
	@Override
	public final String getMime(){
		return MediaType.APPLICATION_XML.toString();
	}
	
}
