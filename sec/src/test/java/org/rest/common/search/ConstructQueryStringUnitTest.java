package org.rest.common.search;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.rest.common.search.ClientOperation.CONTAINS;
import static org.rest.common.search.ClientOperation.EQ;
import static org.rest.common.util.SearchCommonUtil.NEGATION;
import static org.rest.common.util.SearchCommonUtil.OP;
import static org.rest.common.util.SearchCommonUtil.SEPARATOR;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;
import org.rest.common.sec.util.SearchTestUtil;
import org.rest.common.util.QueryConstants;
import org.rest.common.util.SearchField;

public final class ConstructQueryStringUnitTest {

    // not considering negation

    @Test
    public final void whenQueryURIConstructedFromNull_thenNoException() {
        SearchTestUtil.constructQueryString((String) null, null);
    }

    @Test
    public final void whenQueryURIConstructedFromEqId_thenNoException() {
        SearchTestUtil.constructQueryString(randomNumeric(2), null);
    }

    @Test
    public final void whenQueryURIConstructedFromEqName_thenNoException() {
        SearchTestUtil.constructQueryString(null, randomAlphabetic(6));
    }

    @Test
    public final void whenQueryURIConstructedFromEqId_thenQueryStringIsCorrect() {
        final String id = randomNumeric(2);
        final String queryString = SearchTestUtil.constructQueryString(id, null);
        assertEquals(SearchField.id.toString() + OP + id, queryString);
    }

    @Test
    public final void whenQueryURIConstructedFromEqName_thenQueryStringIsCorrect() {
        final String name = randomAlphabetic(6);
        final String queryString = SearchTestUtil.constructQueryString(null, name);
        assertEquals(SearchField.name.toString() + OP + name, queryString);
    }

    @Test
    public final void whenQueryURIConstructedFromEqIdAndEqName_thenQueryStringIsCorrect() {
        final String name = randomAlphabetic(6);
        final String id = randomNumeric(2);
        final String queryString = SearchTestUtil.constructQueryString(id, name);
        assertEquals(SearchField.id.toString() + OP + id + SEPARATOR + SearchField.name.toString() + OP + name, queryString);
    }

    // contains

    @Test
    public final void whenQueryURIConstructedFromContainsName_thenNoExceptions() {
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), CONTAINS, randomAlphabetic(8));
        SearchTestUtil.constructQueryString(null, nameConstraint);
    }

    @Test
    public final void whenQueryURIConstructedFromContainsName_thenQueryStringIsCorrect() {
        final String name = randomAlphabetic(8);
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), CONTAINS, name);
        final String queryString = SearchTestUtil.constructQueryString(null, nameConstraint);

        assertEquals(SearchField.name.toString() + OP + QueryConstants.ANY_CLIENT + name + QueryConstants.ANY_CLIENT, queryString);
    }

    // value for different operations

    @Test
    public final void whenConstructingTheValueForAStringQueryParameter_thenNoExceptions() {
        new SearchUriBuilder().constructStringQueryValue(randomAlphabetic(6), CONTAINS);
    }

    @Test
    public final void givenOperationIsContains_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, CONTAINS);

        // Then
        assertThat(queryValue, equalTo(QueryConstants.ANY_CLIENT + value + QueryConstants.ANY_CLIENT));
    }

    @Test
    public final void givenOperationIsEq_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, EQ);

        // Then
        assertThat(queryValue, equalTo(value));
    }

    @Test
    public final void givenOperationIsNotEq_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, ClientOperation.NEG_EQ);

        // Then
        assertThat(queryValue, equalTo(value));
    }

    @Test
    public final void givenOperationIsNotContains_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, ClientOperation.NEG_CONTAINS);

        // Then
        assertThat(queryValue, equalTo(QueryConstants.ANY_CLIENT + value + QueryConstants.ANY_CLIENT));
    }

    @Test
    public final void givenOperationIsStartsWith_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, ClientOperation.STARTS_WITH);

        // Then
        assertThat(queryValue, equalTo(value + QueryConstants.ANY_CLIENT));
    }

    @Test
    public final void givenOperationIsEndsWith_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, ClientOperation.ENDS_WITH);

        // Then
        assertThat(queryValue, equalTo(QueryConstants.ANY_CLIENT + value));
    }

    @Test
    public final void givenOperationIsNotStartsWith_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, ClientOperation.STARTS_WITH);

        // Then
        assertThat(queryValue, equalTo(value + QueryConstants.ANY_CLIENT));
    }

    @Test
    public final void givenOperationIsNotEndsWith_whenConstructingTheParamValue_thenValueisCorrect() {
        final String value = randomAlphabetic(6);

        // When
        final String queryValue = new SearchUriBuilder().constructStringQueryValue(value, ClientOperation.ENDS_WITH);

        // Then
        assertThat(queryValue, equalTo(QueryConstants.ANY_CLIENT + value));
    }

    // considering negation

    @Test
    public final void whenQueryURIConstructedFromNotEqId_thenNoException() {
        SearchTestUtil.constructQueryString(randomNumeric(2), true, null, false);
    }

    @Test
    public final void whenQueryURIConstructedFromNotEqId_thenQueryStringIsCorrect() {
        final String id = randomNumeric(2);
        final String queryString = SearchTestUtil.constructQueryString(id, true, null, false);
        assertEquals(SearchField.id.toString() + NEGATION + OP + id, queryString);
    }

    @Test
    public final void whenQueryURIConstructedFromNotEqName_thenNoException() {
        SearchTestUtil.constructQueryString(null, false, randomAlphabetic(6), true);
    }

    @Test
    public final void whenQueryURIConstructedFromNotEqName_thenQueryStringIsCorrect() {
        final String name = randomAlphabetic(6);
        final String queryString = SearchTestUtil.constructQueryString(null, false, name, true);
        assertEquals(SearchField.name.toString() + NEGATION + OP + name, queryString);
    }

    @Test
    public final void whenQueryURIConstructedFromIdAndNotEqName_thenNoException() {
        SearchTestUtil.constructQueryString(randomNumeric(2), false, randomAlphabetic(6), true);
    }

    @Test
    public final void whenQueryURIConstructedFromIdAndNegatedName_thenQueryStringIsCorrect() {
        final String name = randomAlphabetic(6);
        final String id = randomNumeric(2);
        final String queryString = SearchTestUtil.constructQueryString(id, false, name, true);
        assertEquals(SearchField.id.toString() + OP + id + SEPARATOR + SearchField.name.toString() + NEGATION + OP + name, queryString);
    }

    @Test
    public final void whenQueryURIConstructedFromNameAndNegatedId_thenQueryStringIsCorrect() {
        final String name = randomAlphabetic(6);
        final String id = randomNumeric(2);
        final String queryString = SearchTestUtil.constructQueryString(id, true, name, false);
        assertEquals(SearchField.id.toString() + NEGATION + OP + id + SEPARATOR + SearchField.name.toString() + OP + name, queryString);
    }

    @Test
    public final void whenQueryURIConstructedFromNegatedNameAndNegatedId_thenQueryStringIsCorrect() {
        final String name = randomAlphabetic(6);
        final String id = randomNumeric(2);
        final String queryString = SearchTestUtil.constructQueryString(id, true, name, true);
        assertEquals(SearchField.id.toString() + NEGATION + OP + id + SEPARATOR + SearchField.name.toString() + NEGATION + OP + name, queryString);
    }

}
