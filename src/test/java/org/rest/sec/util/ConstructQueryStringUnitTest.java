package org.rest.sec.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rest.util.SearchCommonUtil;

public final class ConstructQueryStringUnitTest {

    // not considering negation

    @Test
    public final void whenQueryStringIsConstructedFromNull_thenNoException() {
	SearchTestUtil.constructQueryString((Long) null, null);
    }

    @Test
    public final void whenQueryStringIsConstructedFromId_thenNoException() {
	SearchTestUtil.constructQueryString(2l, null);
    }

    @Test
    public final void whenQueryStringIsConstructedFromName_thenNoException() {
	SearchTestUtil.constructQueryString(null, "someName");
    }

    @Test
    public final void whenQueryStringIsConstructedFromId_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, null);
	assertEquals(SearchCommonUtil.ID + SearchCommonUtil.OP + "2", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(null, "someName");
	assertEquals(SearchCommonUtil.NAME + SearchCommonUtil.OP + "someName", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromIdAndName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(32l, "someName");
	assertEquals(SearchCommonUtil.ID + SearchCommonUtil.OP + "32,name" + SearchCommonUtil.OP + "someName", queryString);
    }

    // considering negation

    @Test
    public final void whenQueryStringIsConstructedFromNegatedId_thenNoException() {
	SearchTestUtil.constructQueryString(2l, true, null, false);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedId_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, true, null, false);
	assertEquals(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "2", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedName_thenNoException() {
	SearchTestUtil.constructQueryString(null, false, "some", true);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(null, false, "some", true);
	assertEquals(SearchCommonUtil.NAME + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "some", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromIdAndNegatedName_thenNoException() {
	SearchTestUtil.constructQueryString(2l, false, "some", true);
    }

    @Test
    public final void whenQueryStringIsConstructedFromIdAndNegatedName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, false, "some", true);
	assertEquals(SearchCommonUtil.ID + SearchCommonUtil.OP + "2," + SearchCommonUtil.NAME + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "some", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNameAndNegatedId_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, true, "some", false);
	assertEquals(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "2," + SearchCommonUtil.NAME + SearchCommonUtil.OP + "some", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedNameAndNegatedId_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, true, "some", true);
	assertEquals(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "2," + SearchCommonUtil.NAME + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "some", queryString);
    }

}
