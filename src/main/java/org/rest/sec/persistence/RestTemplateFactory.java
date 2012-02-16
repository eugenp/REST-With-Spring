package org.rest.sec.persistence;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.sec.dto.User;
import org.rest.sec.model.Privilege;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory implements FactoryBean< RestTemplate >, InitializingBean{
	
	private RestTemplate restTemplate;
	
	@Value( "${port}" ) private int port;
	@Value( "${host}" ) private String host;
	@Value( "${protocol}" ) private String protocol;
	
	//
	
	@Override
	public final RestTemplate getObject(){
		return restTemplate;
	}
	
	@Override
	public final Class< RestTemplate > getObjectType(){
		return RestTemplate.class;
	}
	
	@Override
	public final boolean isSingleton(){
		return true;
	}
	
	@Override
	public final void afterPropertiesSet(){
		final HttpHost targetHost = new HttpHost( host, port, protocol );
		
		final DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials( new AuthScope( targetHost.getHostName(), targetHost.getPort(), AuthScope.ANY_REALM ), new UsernamePasswordCredentials( SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_USERNAME ) );
		final AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		final BasicScheme basicAuth = new BasicScheme();
		authCache.put( targetHost, basicAuth );
		
		restTemplate = new RestTemplate( new HttpComponentsClientHttpRequestFactory( httpclient ){
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
