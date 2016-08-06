package org.baeldung.common.util;

public final class QueryConstants {

    public static final String QUESTIONMARK = "?";

    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_ORDER = "sortOrder";

    public static final String ID = "id"; // is constant because it's used for the controller mapping

    private QueryConstants() {
        throw new AssertionError();
    }

    //

}
