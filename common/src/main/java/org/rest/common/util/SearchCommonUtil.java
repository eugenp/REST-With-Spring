package org.rest.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.rest.common.search.ClientOperation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class SearchCommonUtil {
    public static final String Q_PARAM = "q";

    public static final String SEPARATOR = ",";
    public static final String SEPARATOR_AMPER = "&";

    public static final String OP = "=";
    public static final String NEGATION = "~";

    public static final String ID = "id";
    public static final String NAME = "name";

    private SearchCommonUtil() {
        throw new UnsupportedOperationException();
    }

    // API

    public static List<ImmutableTriple<String, ClientOperation, String>> parseQueryString(final String queryString) {
        Preconditions.checkNotNull(queryString);
        Preconditions.checkState(queryString.matches("((id~?=[0-9]+)?,?)*((name~?=[0-9a-zA-Z*]+),?)*"));

        final List<ImmutableTriple<String, ClientOperation, String>> tuplesList = Lists.newArrayList();
        final String[] tuples = queryString.split(SEPARATOR);
        for (final String tuple : tuples) {
            final String[] keyAndValue = tuple.split(OP);
            Preconditions.checkState(keyAndValue.length == 2);
            tuplesList.add(createConstraintFromUriParam(keyAndValue[0], keyAndValue[1]));
        }

        return tuplesList;
    }

    public static List<ImmutableTriple<String, ClientOperation, ?>> uriParamsToConstraints(final Map<String, String[]> validParameters) {
        final List<ImmutableTriple<String, ClientOperation, ?>> tuplesList = Lists.newArrayList();
        for (final Map.Entry<String, String[]> mapKeyValue : validParameters.entrySet()) {
            tuplesList.add(createConstraintFromUriParam(mapKeyValue.getKey(), mapKeyValue.getValue()[0]));
        }
        return tuplesList;
    }

    public static boolean validateParameters(final Set<String> paramKeys) {
        if (paramKeys.retainAll(Lists.newArrayList(// @formatter:off
                SearchField.name.toString(), QueryConstants.NAME_NEG,

                SearchField.loginName.toString(), QueryConstants.LOGIN_NAME_NEG,

                SearchField.id.toString(), QueryConstants.ID_NEG,

                SearchField.email.toString(), QueryConstants.EMAIL_NEG,

                SearchField.tenant.toString(), QueryConstants.TENANT_NEG,

                SearchField.locked.toString(), QueryConstants.LOCKED_NEG,

                SearchField.description.toString(), QueryConstants.DESCRIPTION_NEG))) { // @formatter:on
            return false;
        }
        return true;
    }

    // util

    static ImmutableTriple<String, ClientOperation, String> createConstraintFromUriParam(final String key, final String value) {
        boolean negated = false;
        if (key.endsWith(NEGATION)) {
            negated = true;
        }

        final ClientOperation op = determineOperation(negated, value);
        final String theKey = determineKey(negated, key);
        final String theValue = determineValue(value);
        return new ImmutableTriple<String, ClientOperation, String>(theKey, op, theValue);
    }

    static String determineValue(final String value) {
        return value.replaceAll("\\*", QueryConstants.ANY_SERVER);
    }

    static String determineKey(final boolean negated, final String key) {
        if (negated) {
            return key.substring(0, key.length() - 1);
        }
        return key;
    }

    static ClientOperation determineOperation(final boolean negated, final String value) {
        ClientOperation op = null;
        if (value.startsWith(QueryConstants.ANY_CLIENT)) {
            if (value.endsWith(QueryConstants.ANY_CLIENT)) {
                op = negated ? ClientOperation.NEG_CONTAINS : ClientOperation.CONTAINS;
            } else {
                op = negated ? ClientOperation.NEG_ENDS_WITH : ClientOperation.ENDS_WITH;
            }
        } else if (value.endsWith(QueryConstants.ANY_CLIENT)) {
            op = negated ? ClientOperation.NEG_STARTS_WITH : ClientOperation.STARTS_WITH;
        } else {
            op = negated ? ClientOperation.NEG_EQ : ClientOperation.EQ;
        }
        return op;
    }

}
