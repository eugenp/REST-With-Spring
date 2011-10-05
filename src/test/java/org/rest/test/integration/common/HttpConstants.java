package org.rest.test.integration.common;

public final class HttpConstants{
	public static final String SET_COOKIE_HEADER = "Set-Cookie";
	public static final String COOKIE_HEADER = "Cookie";
	public static final String ACCEPT_HEADER = "Accept";
	public static final String CONTENT_TYPE_HEADER = "Content-Type";
	
	public static final int SESSIONID_LENGHT = 43;
	
	private HttpConstants(){
		throw new AssertionError();
	}
	
}
