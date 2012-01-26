package org.rest.poc.web.foo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Foo;
import org.rest.poc.testing.template.FooRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.web.common.AbstractRESTDiscoverabilityIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class FooDiscoverabilityRESTIntegrationTest extends AbstractRESTDiscoverabilityIntegrationTest< Foo >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	FooRESTTemplateImpl restTemplate;
	
	public FooDiscoverabilityRESTIntegrationTest(){
		super( Foo.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final String getURI(){
		return paths.getFooUri();
	}

	@Override
	protected final FooRESTTemplateImpl getTemplate(){
		return restTemplate;
	}

	@Override
	protected final void change( final Foo resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected final Foo createNewEntity(){
		return restTemplate.createNewEntity();
	}
}
