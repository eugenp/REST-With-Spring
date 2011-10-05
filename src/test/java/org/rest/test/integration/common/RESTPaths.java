package org.rest.test.integration.common;

public final class RESTPaths{
	
	final static String CONTEXT = "http://localhost:8080/REST/api";
	public final static String HELLO_WORLD_PATH = CONTEXT + "/helloWorld";
	
	private RESTPaths(){
		throw new AssertionError();
	}
	
}
