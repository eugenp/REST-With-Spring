package org.rest.common.util;

public final class RESTURIUtil{
	
	public static final String REL_COLLECTION = "collection";
	
	private RESTURIUtil(){
		throw new AssertionError();
	}
	
	//
	
	public static String createLinkHeader( final String uri, final String rel ){
		return "<" + uri + ">; rel=\"" + rel + "\"";
	}
	
	public static String gatherLinkHeaders( final String... uris ){
		final StringBuilder linkHeaderValue = new StringBuilder();
		for( final String uri : uris ){
			linkHeaderValue.append( uri );
			linkHeaderValue.append( ", " );
		}
		return linkHeaderValue.substring( 0, linkHeaderValue.length() - 2 ).toString();
	}
}
