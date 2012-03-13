package org.rest.sec.util;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.rest.util.SearchCommonUtil;

public final class ParseQueryStringUnitTest {

    // single key-value tuple

    // -- incorrect

    @Test(expected = NullPointerException.class)
    public final void whenNullQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString(null);
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryDoesNotContainConstraintField_whenQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString("justtext");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainIdConstraintFieldButNoValue_whenQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP);
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainIdConstraintNonNumericalValueForId_whenQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "aa");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainSingleIdConstraintWithValidFieldValueAndAdditionalInvalidCharacter_whenQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "aa,");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainSingleIdConstraintWithCorrectFieldValueButInvalidSeparator_whenQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString("id|aa,");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainIdConstraintWithUnknownKey_whenQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString("somekey|aa");
    }

    // -- correct

    @Test
    public final void givenQueryContainsValidIdConstraint_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2");
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultIsNotNull() {
	final List<ImmutablePair<String, ?>> queryTyples = SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2");

	assertFalse(queryTyples.isEmpty());
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultKeyIsCorrect() {
	final List<ImmutablePair<String, ?>> queryTyples = SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2");
	final ImmutablePair<String, ?> pair = queryTyples.get(0);
	assertEquals(SearchCommonUtil.ID, pair.getLeft());
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultValueIsCorrect() {
	final List<ImmutablePair<String, ?>> queryTyples = SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2");
	final ImmutablePair<String, ?> pair = queryTyples.get(0);
	assertEquals(2l, pair.getRight());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.NAME + SearchCommonUtil.OP + "some");
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultIsNotNull() {
	final List<ImmutablePair<String, ?>> queryTyples = SearchCommonUtil.parseQueryString(SearchCommonUtil.NAME + SearchCommonUtil.OP + "some");

	assertFalse(queryTyples.isEmpty());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultKeyIsCorrect() {
	final List<ImmutablePair<String, ?>> queryTyples = SearchCommonUtil.parseQueryString(SearchCommonUtil.NAME + SearchCommonUtil.OP + "some");
	final ImmutablePair<String, ?> pair = queryTyples.get(0);
	assertEquals(SearchCommonUtil.NAME, pair.getLeft());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultValueIsCorrect() {
	final List<ImmutablePair<String, ?>> queryTyples = SearchCommonUtil.parseQueryString(SearchCommonUtil.NAME + SearchCommonUtil.OP + "some");
	final ImmutablePair<String, ?> pair = queryTyples.get(0);
	assertEquals("some", pair.getRight());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect() {
	final List<ImmutablePair<String, ?>> queryTyples = SearchCommonUtil.parseQueryString(SearchCommonUtil.NAME + SearchCommonUtil.OP + "Some");
	final ImmutablePair<String, ?> pair = queryTyples.get(0);
	assertEquals("Some", pair.getRight());
    }

    // -- correct - with negation

    @Test
    public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "2");
    }

    @Test
    public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.NAME + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "some");
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "3" + SearchCommonUtil.SEPARATOR + SearchCommonUtil.NAME + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "some");
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "3" + SearchCommonUtil.SEPARATOR + SearchCommonUtil.NAME + SearchCommonUtil.OP + "some");
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenResultsAreCorrect() {
	final List<ImmutablePair<String, ?>> parsedQueryString = SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "3" + SearchCommonUtil.SEPARATOR + SearchCommonUtil.NAME + SearchCommonUtil.OP
		+ "some");

	assertEquals(3l, parsedQueryString.get(0).getRight());
	assertEquals("some", parsedQueryString.get(1).getRight());
    }

    @Test
    public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenResultTypesAreCorrect() {
	final List<ImmutablePair<String, ?>> parseQueryString = SearchCommonUtil.parseQueryString(SearchCommonUtil.NAME + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "some");
	assertTrue(parseQueryString.get(0).getRight().getClass().equals(String.class));
    }

    @Test
    public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenResultTypesAreCorrect() {
	final List<ImmutablePair<String, ?>> parseQueryString = SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.NEGATION + SearchCommonUtil.OP + "2");
	assertTrue(parseQueryString.get(0).getRight().getClass().equals(Long.class));
    }

    // multiple key-value tuples

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainOneValidConstraintAndOneIncorrectOne_whenQueryIsParsed_thenException() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2" + SearchCommonUtil.SEPARATOR + "name");
    }

    @Test
    public final void givenQueryContainANameConstraintWithNumbersInTheName_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2" + SearchCommonUtil.SEPARATOR + SearchCommonUtil.NAME + SearchCommonUtil.OP + "eugen2");
    }

    @Test
    public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenNoExceptions() {
	SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2" + SearchCommonUtil.SEPARATOR + SearchCommonUtil.NAME + SearchCommonUtil.OP + "eugen");
    }

    @Test
    public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenConstraintsAreCorrect() {
	final List<ImmutablePair<String, ?>> parsedQueryString = SearchCommonUtil.parseQueryString(SearchCommonUtil.ID + SearchCommonUtil.OP + "2" + SearchCommonUtil.SEPARATOR + SearchCommonUtil.NAME + SearchCommonUtil.OP + "eugen");
	assertThat(parsedQueryString, hasItem(new ImmutablePair<String, Long>(SearchCommonUtil.ID, 2l)));
	assertThat(parsedQueryString, hasItem(new ImmutablePair<String, String>(SearchCommonUtil.NAME, "eugen")));
    }

}
