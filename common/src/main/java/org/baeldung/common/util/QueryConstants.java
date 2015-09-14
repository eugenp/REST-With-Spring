package org.baeldung.common.util;

public final class QueryConstants {

    public static final String ID_NEG = SearchField.id.toString() + QueryConstants.NEGATION;
    public static final String NAME_NEG = SearchField.name.toString() + QueryConstants.NEGATION;
    public static final String LOGIN_NAME_NEG = SearchField.loginName.toString() + QueryConstants.NEGATION;
    public static final String EMAIL_NEG = SearchField.email.toString() + QueryConstants.NEGATION;
    public static final String TENANT_NEG = SearchField.tenant.toString() + QueryConstants.NEGATION;
    public static final String LOCKED_NEG = SearchField.locked.toString() + QueryConstants.NEGATION;
    public static final String DESCRIPTION_NEG = SearchField.description.toString() + QueryConstants.NEGATION;

    public static final String QUESTIONMARK = "?";

    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_ORDER = "sortOrder";
    public static final String Q_SORT_BY = QUESTIONMARK + SORT_BY + QueryConstants.OP;
    public static final String S_ORDER = QueryConstants.SEPARATOR_AMPER + QueryConstants.SORT_ORDER + QueryConstants.OP;
    public static final String S_ORDER_ASC = S_ORDER + "ASC";
    public static final String S_ORDER_DESC = S_ORDER + "DESC";

    /** - note: this character represents the ANY wildcard for the server side (persistence layer) */
    public static final String ANY_SERVER = "%";
    /** - note: this character represents the ANY wildcard for the client consumption of the API */
    public static final String ANY_CLIENT = "*";
    public static final String QUERY_PREFIX = QUESTIONMARK + "q=";
    public static final String Q_PARAM = "q";
    public static final String SEPARATOR = ",";
    public static final String SEPARATOR_AMPER = "&";
    public static final String OP = "=";
    public static final String NEGATION = "~";

    public static final String ID = "id"; // is constant because it's used for the controller mapping
    public static final String NAME = SearchField.name.toString();
    public static final String UUID = "uuid";

    private QueryConstants() {
        throw new AssertionError();
    }

    //

}
