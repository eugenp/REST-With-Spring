package org.rest.client.template.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.rest.client.template.AbstractRESTTemplate;
import org.rest.sec.client.ExamplePaths;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.util.SearchUtil;
import org.rest.testing.security.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@Component
public final class RoleRESTTemplateImpl extends AbstractRESTTemplate< Role >{
	
	@Autowired protected ExamplePaths paths;
	
	public RoleRESTTemplateImpl(){
		super( Role.class );
	}
	
	// API
	
	public final Role findByName( final String name ){
		final String resourceAsXML = findOneAsMime( getURI() + "?name=" + name );
		return marshaller.decode( resourceAsXML, clazz );
	}

	@Override
	public final Response searchAsResponse( final Pair< Long, ClientOperations > idOp, final Pair< String, ClientOperations > nameOp ){
		final String queryURI = getURI() + "?q=" + SearchUtil.constructQueryString( idOp, nameOp );
		return givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( queryURI );
	}
	
	@Override
	public final Response searchAsResponse( final Long id, final String name ){
		final String queryURI = getURI() + "?q=" + SearchUtil.constructQueryString( id, name );
		return givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( queryURI );
	}
	
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Override
	public final List< Role > search( final Long id, final String name ){
		final String queryURI = getURI() + "?q=" + SearchUtil.constructQueryString( id, name );
		final Response searchResponse = givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( queryURI );
		Preconditions.checkState( searchResponse.getStatusCode() == 200 );
		
		return getMarshaller().<List> decode( searchResponse.getBody().asString(), List.class );
	}
	
	// template method
	
	@Override
	public final String getURI(){
		return paths.getRoleUri();
	}
	
	@Override
	public final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticated();
	}
	
	@Override
	public final Role createNewEntity(){
		return new Role( randomAlphabetic( 8 ), Sets.<Privilege> newHashSet() );
	}
	@Override
	public final void invalidate( final Role entity ){
		entity.setName( null );
	}
	@Override
	public final void change( final Role resource ){
		resource.setName( randomAlphabetic( 8 ) );
	}
	
}
