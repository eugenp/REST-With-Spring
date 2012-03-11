package org.rest.sec.util;

import static org.junit.Assert.assertEquals;
import static org.rest.sec.util.SearchUtil.ID;
import static org.rest.sec.util.SearchUtil.NAME;
import static org.rest.sec.util.SearchUtil.NEGATION;
import static org.rest.sec.util.SearchUtil.OP;

import org.junit.Test;

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
	assertEquals(ID + OP + "2", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(null, "someName");
	assertEquals(NAME + OP + "someName", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromIdAndName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(32l, "someName");
	assertEquals(ID + OP + "32,name" + OP + "someName", queryString);
    }

    // considering negation

    @Test
    public final void whenQueryStringIsConstructedFromNegatedId_thenNoException() {
	SearchTestUtil.constructQueryString(2l, true, null, false);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedId_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, true, null, false);
	assertEquals(ID + NEGATION + OP + "2", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedName_thenNoException() {
	SearchTestUtil.constructQueryString(null, false, "some", true);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(null, false, "some", true);
	assertEquals(NAME + NEGATION + OP + "some", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromIdAndNegatedName_thenNoException() {
	SearchTestUtil.constructQueryString(2l, false, "some", true);
    }

    @Test
    public final void whenQueryStringIsConstructedFromIdAndNegatedName_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, false, "some", true);
	assertEquals(ID + OP + "2," + NAME + NEGATION + OP + "some", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNameAndNegatedId_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, true, "some", false);
	assertEquals(ID + NEGATION + OP + "2," + NAME + OP + "some", queryString);
    }

    @Test
    public final void whenQueryStringIsConstructedFromNegatedNameAndNegatedId_thenQueryStringIsCorrect() {
	final String queryString = SearchTestUtil.constructQueryString(2l, true, "some", true);
	assertEquals(ID + NEGATION + OP + "2," + NAME + NEGATION + OP + "some", queryString);
    }

}
