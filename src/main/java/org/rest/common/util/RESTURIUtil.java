package org.rest.common.util;

public final class RESTURIUtil {

    public static final String REL_COLLECTION = "collection";
    public static final String REL_NEXT = "next";
    public static final String REL_PREV = "prev";
    public static final String REL_FIRST = "first";
    public static final String REL_LAST = "last";

    private RESTURIUtil() {
	throw new AssertionError();
    }

    //

    public static String createLinkHeader(final String uri, final String rel) {
	return "<" + uri + ">; rel=\"" + rel + "\"";
    }

    public static String gatherLinkHeaders(final String... uris) {
	final StringBuilder linkHeaderValue = new StringBuilder();
	for (final String uri : uris) {
	    linkHeaderValue.append(uri);
	    linkHeaderValue.append(", ");
	}
	return linkHeaderValue.substring(0, linkHeaderValue.length() - 2).toString();
    }

}
