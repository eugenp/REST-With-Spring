package org.rest.spring.web;

import java.util.List;

import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.model.dto.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan( { "org.rest.web", "org.rest.sec.web" } )
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{
	
	public WebConfig(){
		super();
	}
	
	// beans
	
	@Bean
	public XStreamMarshaller xstreamMarshaller(){
		final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		xStreamMarshaller.setAutodetectAnnotations( true );
		xStreamMarshaller.setAnnotatedClasses( new Class[ ] { User.class, Role.class, Privilege.class } );
		xStreamMarshaller.getXStream().addDefaultImplementation( java.sql.Timestamp.class, java.util.Date.class );
		
		return xStreamMarshaller;
	}
	
	@Bean
	public MarshallingHttpMessageConverter marshallingHttpMessageConverter(){
		final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
		final XStreamMarshaller xstreamMarshaller = xstreamMarshaller();
		marshallingHttpMessageConverter.setMarshaller( xstreamMarshaller );
		marshallingHttpMessageConverter.setUnmarshaller( xstreamMarshaller );
		
		return marshallingHttpMessageConverter;
	}
	
	@Override
	public void configureMessageConverters( final List< HttpMessageConverter< ? >> converters ){
		super.configureMessageConverters( converters );
		
		converters.add( marshallingHttpMessageConverter() );
	}
	
}
