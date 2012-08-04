package org.rest.common.util;

import org.springframework.data.domain.Sort;

public final class QueryConstants {

    public static final String ID_NEG = SearchField.id.toString() + SearchCommonUtil.NEGATION;
    public static final String NAME_NEG = SearchField.name.toString() + SearchCommonUtil.NEGATION;
    public static final String LOGIN_NAME_NEG = SearchField.loginName.toString() + SearchCommonUtil.NEGATION;
    public static final String EMAIL_NEG = SearchField.email.toString() + SearchCommonUtil.NEGATION;
    public static final String TENANT_NEG = SearchField.tenant.toString() + SearchCommonUtil.NEGATION;
    public static final String LOCKED_NEG = SearchField.locked.toString() + SearchCommonUtil.NEGATION;
    public static final String DESCRIPTION_NEG = SearchField.description.toString() + SearchCommonUtil.NEGATION;

    public static final String QUESTIONMARK = "?";

    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_ORDER = "sortOrder";
    public static final String Q_SORT_BY = QUESTIONMARK + SORT_BY + SearchCommonUtil.OP;
    public static final String S_ORDER = SearchCommonUtil.SEPARATOR_AMPER + QueryConstants.SORT_ORDER + SearchCommonUtil.OP;
    public static final String S_ORDER_ASC = S_ORDER + Sort.Direction.ASC.name();
    public static final String S_ORDER_DESC = S_ORDER + Sort.Direction.DESC.name();

    /** - note: this character represents the ANY wildcard for the server side (persistence layer) */
    public static final String ANY_SERVER = "%";
    /** - note: this character represents the ANY wildcard for the client consumption of the API */
    public static final String ANY_CLIENT = "*";

    private QueryConstants() {
        throw new AssertionError();
    }

    //

}
