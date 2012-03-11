package org.rest.sec.util;

import static org.rest.sec.util.SearchUtil.ID;
import static org.rest.sec.util.SearchUtil.NAME;
import static org.rest.sec.util.SearchUtil.NEGATION;
import static org.rest.sec.util.SearchUtil.OP;
import static org.rest.sec.util.SearchUtil.SEPARATOR;

import org.apache.commons.lang3.tuple.Pair;
import org.rest.client.template.impl.ClientOperations;

public final class SearchTestUtil {
    private SearchTestUtil() {
	throw new UnsupportedOperationException();
    }

    //

    public static String constructQueryString(final Long id, final String name) {
	return constructQueryString(id, false, name, false);
    }

    public static String constructQueryString(final Pair<Long, ClientOperations> idOp, final Pair<String, ClientOperations> nameOp) {
	final Long id = (idOp == null) ? null : idOp.getLeft();
	final boolean negatedId = (idOp == null) ? false : idOp.getRight().isNegated();
	final String name = (nameOp == null) ? null : nameOp.getLeft();
	final boolean negatedName = (nameOp == null) ? false : nameOp.getRight().isNegated();
	return constructQueryString(id, negatedId, name, negatedName);
    }

    static String constructQueryString(final Long id, final boolean negatedId, final String name, final boolean negatedName) {
	final StringBuffer queryString = new StringBuffer();
	String op = null;
	if (id != null) {
	    op = (negatedId) ? SearchUtil.NEGATION + OP : OP;
	    queryString.append(ID + op + id);
	}
	if (name != null) {
	    if (queryString.length() != 0) {
		queryString.append(SEPARATOR);
	    }
	    op = (negatedName) ? NEGATION + OP : OP;
	    queryString.append(NAME + op + name);
	}

	return queryString.toString();
    }

}
