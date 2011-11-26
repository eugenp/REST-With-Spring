package org.rest.common.util;

public final class HttpConstants{
	public static final String SET_COOKIE_HEADER = "Set-Cookie";
	public static final String COOKIE_HEADER = "Cookie";
	public static final String LINK_HEADER = "Link";
	
	public static final String MIME_JSON = "application/json";
	public static final String MIME_XML = "application/xml";
	public static final String MIME_ATOM = "application/atom+xml";
	
	private HttpConstants(){
		throw new AssertionError();
	}
	
}
