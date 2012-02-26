package org.rest.sec.persistence;

import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.sec.dto.User;
import org.rest.sec.model.Privilege;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory implements FactoryBean< RestTemplate >, InitializingBean{
	private RestTemplate restTemplate;
	
	@Override
	public RestTemplate getObject(){
		return restTemplate;
	}
	
	@Override
	public Class< RestTemplate > getObjectType(){
		return RestTemplate.class;
	}
	
	@Override
	public boolean isSingleton(){
		return true;
	}
	
	@Override
	public void afterPropertiesSet(){
		restTemplate = new RestTemplate( new HttpComponentsClientHttpRequestFactory( new DefaultHttpClient() ){
			{
				setReadTimeout( 15000 );
			}
		} );
		restTemplate.getMessageConverters().add( marshallingHttpMessageConverter() );
	}
	
	//
	
	final MarshallingHttpMessageConverter marshallingHttpMessageConverter(){
		final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
		marshallingHttpMessageConverter.setMarshaller( xstreamMarshaller() );
		marshallingHttpMessageConverter.setUnmarshaller( xstreamMarshaller() );
		
		return marshallingHttpMessageConverter;
	}
	final XStreamMarshaller xstreamMarshaller(){
		final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		xStreamMarshaller.setAutodetectAnnotations( true );
		// xStreamMarshaller.setSupportedClasses( new Class[ ] { User.class, Privilege.class } );
		xStreamMarshaller.setAnnotatedClass( User.class );
		xStreamMarshaller.setAnnotatedClass( Privilege.class );
		
		// this.xstreamMarshaller().getXStream().addDefaultImplementation( java.util.HashSet.class, PersistentSet.class );
		
		return xStreamMarshaller;
	}
}
