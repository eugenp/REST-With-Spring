package org.rest.controller.discovery;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eugenp
 */
@Controller
final class DiscoveryController{
	
	public DiscoveryController(){
		super();
	}
	
	// API
	
	@RequestMapping( value = "",method = RequestMethod.GET )
	@ResponseBody
	@ResponseStatus( value = HttpStatus.NO_CONTENT )
	public final String root(){
		return "/foo";
	}
	
}
