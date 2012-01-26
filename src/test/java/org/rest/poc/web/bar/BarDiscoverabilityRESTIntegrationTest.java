package org.rest.poc.web.bar;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Bar;
import org.rest.poc.testing.template.BarRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.web.common.AbstractRESTDiscoverabilityIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class BarDiscoverabilityRESTIntegrationTest extends AbstractRESTDiscoverabilityIntegrationTest< Bar >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	BarRESTTemplateImpl restTemplate;
	
	public BarDiscoverabilityRESTIntegrationTest(){
		super( Bar.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final String getURI(){
		return paths.getFooUri();
	}

	@Override
	protected final BarRESTTemplateImpl getTemplate(){
		return restTemplate;
	}

	@Override
	protected final void change( final Bar resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected final Bar createNewEntity(){
		return restTemplate.createNewEntity();
	}
}
