package org.baeldung.common.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.baeldung.common.util.SearchCommonUtil.parseQueryString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.common.search.ClientOperation;
import org.baeldung.common.util.QueryConstants;
import org.baeldung.common.util.SearchField;
import org.junit.Test;

public final class ParseQueryStringUnitTest {

    // single key-value tuple

    // -- incorrect

    @Test(expected = NullPointerException.class)
    public final void whenNullQueryIsParsed_thenException() {
        parseQueryString(null);
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryDoesNotContainConstraintField_whenQueryIsParsed_thenException() {
        parseQueryString("justtext");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainIdConstraintFieldButNoValue_whenQueryIsParsed_thenException() {
        parseQueryString(QueryConstants.ID + QueryConstants.OP);
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainIdConstraintNonNumericalValueForId_whenQueryIsParsed_thenException() {
        parseQueryString(QueryConstants.ID + QueryConstants.OP + "aa");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainSingleIdConstraintWithValidFieldValueAndAdditionalInvalidCharacter_whenQueryIsParsed_thenException() {
        parseQueryString(QueryConstants.ID + QueryConstants.OP + "aa,");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainSingleIdConstraintWithCorrectFieldValueButInvalidSeparator_whenQueryIsParsed_thenException() {
        parseQueryString("id|aa,");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainIdConstraintWithUnknownKey_whenQueryIsParsed_thenException() {
        parseQueryString("somekey|aa");
    }

    // -- correct

    @Test
    public final void givenQueryContainsValidIdConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.ID + QueryConstants.OP + randomNumeric(2));
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultIsNotNull() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.ID + QueryConstants.OP + "2");

        assertFalse(queryTyples.isEmpty());
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultKeyIsCorrect() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.ID + QueryConstants.OP + randomNumeric(2));
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals(QueryConstants.ID, pair.getLeft());
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultValueIsCorrect() {
        final String id = randomNumeric(2);
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.ID + QueryConstants.OP + id);
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals(id, pair.getRight());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.NAME + QueryConstants.OP + "some");
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultIsNotNull() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.NAME + QueryConstants.OP + "some");

        assertFalse(queryTyples.isEmpty());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultKeyIsCorrect() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.NAME + QueryConstants.OP + "some");
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals(QueryConstants.NAME, pair.getLeft());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultValueIsCorrect() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.NAME + QueryConstants.OP + "some");
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals("some", pair.getRight());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.NAME + QueryConstants.OP + "Some");
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals("Some", pair.getRight());
    }

    // correct - with uncommon characters

    @Test
    public final void givenValidQueryWithUncommonCharacters_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.NAME + QueryConstants.OP + "i-98d522e6");
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals("i-98d522e6", pair.getRight());
    }

    @Test
    public final void givenValidQueryWithMoreUncommonCharacters_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.NAME + QueryConstants.OP + "us-east-1/i-98d522e6");
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals("us-east-1/i-98d522e6", pair.getRight());
    }

    @Test
    public final void givenValidQueryWithMoreUncommonCharacters2_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect() {
        final List<Triple<String, ClientOperation, String>> queryTyples = parseQueryString(QueryConstants.NAME + QueryConstants.OP + "ROLE_ORGANIZATION");
        final Triple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals("ROLE_ORGANIZATION", pair.getRight());
    }

    // -- correct - with negation

    @Test
    public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.ID + QueryConstants.NEGATION + QueryConstants.OP + randomNumeric(2));
    }

    @Test
    public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.NAME + QueryConstants.NEGATION + QueryConstants.OP + randomAlphabetic(6));
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.ID + QueryConstants.NEGATION + QueryConstants.OP + "3" + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.NEGATION + QueryConstants.OP + "some");
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.ID + QueryConstants.NEGATION + QueryConstants.OP + "3" + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + "some");
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenResultsAreCorrect() {
        final String id = randomNumeric(2);
        final String name = randomAlphabetic(6);
        final List<Triple<String, ClientOperation, String>> parsedQueryString = parseQueryString(QueryConstants.ID + QueryConstants.NEGATION + QueryConstants.OP + id + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + name);

        assertEquals(id, parsedQueryString.get(0).getRight());
        assertEquals(name, parsedQueryString.get(1).getRight());
    }

    @Test
    public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenResultTypesAreCorrect() {
        final String name = randomAlphabetic(8);
        final List<Triple<String, ClientOperation, String>> parseQueryString = parseQueryString(QueryConstants.NAME + QueryConstants.NEGATION + QueryConstants.OP + name);
        assertTrue(parseQueryString.get(0).getRight().getClass().equals(String.class));
        assertTrue(parseQueryString.get(0).getRight().equals(name));
        assertTrue(parseQueryString.get(0).getLeft().equals(QueryConstants.NAME));
    }

    @Test
    public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenResultTypesAreCorrect() {
        final String id = randomNumeric(2);
        final List<Triple<String, ClientOperation, String>> parseQueryString = parseQueryString(QueryConstants.ID + QueryConstants.NEGATION + QueryConstants.OP + id);
        assertTrue(parseQueryString.get(0).getRight().getClass().equals(String.class));
        assertTrue(parseQueryString.get(0).getRight().equals(id));
        assertTrue(parseQueryString.get(0).getLeft().equals(QueryConstants.ID));
    }

    // correct - with startsWith, endsWith, contains operations

    @Test
    public final void givenQueryContainsValidOpStartsWithNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(SearchField.name.toString() + QueryConstants.OP + randomAlphabetic(8) + "*");
    }

    // multiple key-value tuples

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainOneValidConstraintAndOneIncorrectOne_whenQueryIsParsed_thenException() {
        parseQueryString(QueryConstants.ID + QueryConstants.OP + randomNumeric(2) + QueryConstants.SEPARATOR + "name");
    }

    @Test
    public final void givenQueryContainANameConstraintWithNumbersInTheName_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.ID + QueryConstants.OP + randomNumeric(2) + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + randomAlphabetic(6));
    }

    @Test
    public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(QueryConstants.ID + QueryConstants.OP + "2" + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + "eugen");
    }

    @Test
    public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String id = randomNumeric(2);
        final String name = randomAlphabetic(6);
        final String queryString = QueryConstants.ID + QueryConstants.OP + id + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + name;
        final List<Triple<String, ClientOperation, String>> parsedQueryConstraints = parseQueryString(queryString);
        assertThat(parsedQueryConstraints, hasItem(createNewImmutableTriple(QueryConstants.ID, ClientOperation.EQ, id)));
        assertThat(parsedQueryConstraints, hasItem(createNewImmutableTriple(QueryConstants.NAME, ClientOperation.EQ, name)));
    }

    // multiple key-value tuples (for the same key): ex: id=2,id=3

    @Test
    public final void givenQueryContainTwoValidConstraintsForTheIdKey_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String id1 = randomNumeric(2);
        final String id2 = randomNumeric(2);
        final String queryString = QueryConstants.ID + QueryConstants.OP + id1 + QueryConstants.SEPARATOR + QueryConstants.ID + QueryConstants.OP + id2;
        final List<Triple<String, ClientOperation, String>> parsedQueryString = parseQueryString(queryString);
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.ID, ClientOperation.EQ, id1)));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.ID, ClientOperation.EQ, id2)));
    }

    @Test
    public final void givenQueryContainTwoValidConstraintsForTheNameKey_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String queryString = QueryConstants.NAME + QueryConstants.OP + "me" + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + "andyou";
        final List<Triple<String, ClientOperation, String>> parsedQueryString = parseQueryString(queryString);
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.NAME, ClientOperation.EQ, "me")));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.NAME, ClientOperation.EQ, "andyou")));
    }

    /**
     * - note: the order of constraints in the query language should be alphabetic (id then name)
     */
    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainTwoValidNameConstraintsAndTwoValidIdConstraintsInTheIncorrectOrder_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String queryString = QueryConstants.NAME + QueryConstants.OP + "me" + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + "andyou" + QueryConstants.SEPARATOR + QueryConstants.ID + QueryConstants.OP + "2"
                + QueryConstants.SEPARATOR + QueryConstants.ID + QueryConstants.OP + "3";
        parseQueryString(queryString);
    }

    @Test
    public final void givenQueryContainTwoValidIdConstraintsAndTwoValidNameConstraints_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String id1 = randomNumeric(2);
        final String id2 = randomNumeric(2);
        final String queryString = QueryConstants.ID + QueryConstants.OP + id1 + QueryConstants.SEPARATOR + QueryConstants.ID + QueryConstants.OP + id2 + QueryConstants.SEPARATOR + QueryConstants.NAME + QueryConstants.OP + "me" + QueryConstants.SEPARATOR
                + QueryConstants.NAME + QueryConstants.OP + "andyou";
        final List<Triple<String, ClientOperation, String>> parsedQueryString = parseQueryString(queryString);

        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.ID, ClientOperation.EQ, id1)));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.ID, ClientOperation.EQ, id2)));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.NAME, ClientOperation.EQ, "me")));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(QueryConstants.NAME, ClientOperation.EQ, "andyou")));
    }

    //

    Triple<String, ClientOperation, String> createNewImmutableTriple(final String key, final ClientOperation op, final String value) {
        return new ImmutableTriple<String, ClientOperation, String>(key, op, value);
    }

}
