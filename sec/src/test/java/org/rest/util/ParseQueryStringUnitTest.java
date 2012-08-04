package org.rest.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.rest.common.util.SearchCommonUtil.ID;
import static org.rest.common.util.SearchCommonUtil.NAME;
import static org.rest.common.util.SearchCommonUtil.NEGATION;
import static org.rest.common.util.SearchCommonUtil.OP;
import static org.rest.common.util.SearchCommonUtil.SEPARATOR;
import static org.rest.common.util.SearchCommonUtil.parseQueryString;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.Test;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.SearchField;

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
        parseQueryString(ID + OP);
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainIdConstraintNonNumericalValueForId_whenQueryIsParsed_thenException() {
        parseQueryString(ID + OP + "aa");
    }

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainSingleIdConstraintWithValidFieldValueAndAdditionalInvalidCharacter_whenQueryIsParsed_thenException() {
        parseQueryString(ID + OP + "aa,");
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
        parseQueryString(ID + OP + randomNumeric(2));
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultIsNotNull() {
        final List<ImmutableTriple<String, ClientOperation, String>> queryTyples = parseQueryString(ID + OP + "2");

        assertFalse(queryTyples.isEmpty());
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultKeyIsCorrect() {
        final List<ImmutableTriple<String, ClientOperation, String>> queryTyples = parseQueryString(ID + OP + randomNumeric(2));
        final ImmutableTriple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals(ID, pair.getLeft());
    }

    @Test
    public final void givenValidQueryWithIdConstraint_whenQueryIsParsed_thenResultValueIsCorrect() {
        final String id = randomNumeric(2);
        final List<ImmutableTriple<String, ClientOperation, String>> queryTyples = parseQueryString(ID + OP + id);
        final ImmutableTriple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals(id, pair.getRight());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(NAME + OP + "some");
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultIsNotNull() {
        final List<ImmutableTriple<String, ClientOperation, String>> queryTyples = parseQueryString(NAME + OP + "some");

        assertFalse(queryTyples.isEmpty());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultKeyIsCorrect() {
        final List<ImmutableTriple<String, ClientOperation, String>> queryTyples = parseQueryString(NAME + OP + "some");
        final ImmutableTriple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals(NAME, pair.getLeft());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsed_thenResultValueIsCorrect() {
        final List<ImmutableTriple<String, ClientOperation, String>> queryTyples = parseQueryString(NAME + OP + "some");
        final ImmutableTriple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals("some", pair.getRight());
    }

    @Test
    public final void givenValidQueryWithNameConstraint_whenQueryIsParsedForNameValueWithUppercase_thenResultValueIsCorrect() {
        final List<ImmutableTriple<String, ClientOperation, String>> queryTyples = parseQueryString(NAME + OP + "Some");
        final ImmutableTriple<String, ClientOperation, String> pair = queryTyples.get(0);
        assertEquals("Some", pair.getRight());
    }

    // -- correct - with negation

    @Test
    public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(ID + NEGATION + OP + randomNumeric(2));
    }

    @Test
    public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(NAME + NEGATION + OP + randomAlphabetic(6));
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndNegatedNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(ID + NEGATION + OP + "3" + SEPARATOR + NAME + NEGATION + OP + "some");
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(ID + NEGATION + OP + "3" + SEPARATOR + NAME + OP + "some");
    }

    @Test
    public final void givenQueryContainsValidNegatedIdAndPositiveNameConstraint_whenQueryIsParsed_thenResultsAreCorrect() {
        final String id = randomNumeric(2);
        final String name = randomAlphabetic(6);
        final List<ImmutableTriple<String, ClientOperation, String>> parsedQueryString = parseQueryString(ID + NEGATION + OP + id + SEPARATOR + NAME + OP + name);

        assertEquals(id, parsedQueryString.get(0).getRight());
        assertEquals(name, parsedQueryString.get(1).getRight());
    }

    @Test
    public final void givenQueryContainsValidNegatedNameConstraint_whenQueryIsParsed_thenResultTypesAreCorrect() {
        final String name = randomAlphabetic(8);
        final List<ImmutableTriple<String, ClientOperation, String>> parseQueryString = parseQueryString(NAME + NEGATION + OP + name);
        assertTrue(parseQueryString.get(0).getRight().getClass().equals(String.class));
        assertTrue(parseQueryString.get(0).getRight().equals(name));
        assertTrue(parseQueryString.get(0).getLeft().equals(NAME));
    }

    @Test
    public final void givenQueryContainsValidNegatedIdConstraint_whenQueryIsParsed_thenResultTypesAreCorrect() {
        final String id = randomNumeric(2);
        final List<ImmutableTriple<String, ClientOperation, String>> parseQueryString = parseQueryString(ID + NEGATION + OP + id);
        assertTrue(parseQueryString.get(0).getRight().getClass().equals(String.class));
        assertTrue(parseQueryString.get(0).getRight().equals(id));
        assertTrue(parseQueryString.get(0).getLeft().equals(ID));
    }

    // correct - with startsWith, endsWith, contains operations

    @Test
    public final void givenQueryContainsValidOpStartsWithNameConstraint_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(SearchField.name.toString() + OP + randomAlphabetic(8) + "*");
    }

    // multiple key-value tuples

    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainOneValidConstraintAndOneIncorrectOne_whenQueryIsParsed_thenException() {
        parseQueryString(ID + OP + randomNumeric(2) + SEPARATOR + "name");
    }

    @Test
    public final void givenQueryContainANameConstraintWithNumbersInTheName_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(ID + OP + randomNumeric(2) + SEPARATOR + NAME + OP + randomAlphabetic(6));
    }

    @Test
    public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenNoExceptions() {
        parseQueryString(ID + OP + "2" + SEPARATOR + NAME + OP + "eugen");
    }

    @Test
    public final void givenQueryContainTwoValidConstraints_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String id = randomNumeric(2);
        final String name = randomAlphabetic(6);
        final String queryString = ID + OP + id + SEPARATOR + NAME + OP + name;
        final List<ImmutableTriple<String, ClientOperation, String>> parsedQueryConstraints = parseQueryString(queryString);
        assertThat(parsedQueryConstraints, hasItem(createNewImmutableTriple(ID, ClientOperation.EQ, id)));
        assertThat(parsedQueryConstraints, hasItem(createNewImmutableTriple(NAME, ClientOperation.EQ, name)));
    }

    // multiple key-value tuples (for the same key): ex: id=2,id=3

    @Test
    public final void givenQueryContainTwoValidConstraintsForTheIdKey_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String id1 = randomNumeric(2);
        final String id2 = randomNumeric(2);
        final String queryString = ID + OP + id1 + SEPARATOR + ID + OP + id2;
        final List<ImmutableTriple<String, ClientOperation, String>> parsedQueryString = parseQueryString(queryString);
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(ID, ClientOperation.EQ, id1)));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(ID, ClientOperation.EQ, id2)));
    }

    @Test
    public final void givenQueryContainTwoValidConstraintsForTheNameKey_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String queryString = NAME + OP + "me" + SEPARATOR + NAME + OP + "andyou";
        final List<ImmutableTriple<String, ClientOperation, String>> parsedQueryString = parseQueryString(queryString);
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(NAME, ClientOperation.EQ, "me")));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(NAME, ClientOperation.EQ, "andyou")));
    }

    /**
     * - note: the order of constraints in the query language should be alphabetic (id then name)
     */
    @Test(expected = IllegalStateException.class)
    public final void givenQueryContainTwoValidNameConstraintsAndTwoValidIdConstraintsInTheIncorrectOrder_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String queryString = NAME + OP + "me" + SEPARATOR + NAME + OP + "andyou" + SEPARATOR + ID + OP + "2" + SEPARATOR + ID + OP + "3";
        parseQueryString(queryString);
    }

    @Test
    public final void givenQueryContainTwoValidIdConstraintsAndTwoValidNameConstraints_whenQueryIsParsed_thenConstraintsAreCorrect() {
        final String id1 = randomNumeric(2);
        final String id2 = randomNumeric(2);
        final String queryString = ID + OP + id1 + SEPARATOR + ID + OP + id2 + SEPARATOR + NAME + OP + "me" + SEPARATOR + NAME + OP + "andyou";
        final List<ImmutableTriple<String, ClientOperation, String>> parsedQueryString = parseQueryString(queryString);

        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(ID, ClientOperation.EQ, id1)));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(ID, ClientOperation.EQ, id2)));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(NAME, ClientOperation.EQ, "me")));
        assertThat(parsedQueryString, hasItem(createNewImmutableTriple(NAME, ClientOperation.EQ, "andyou")));
    }

    //

    ImmutableTriple<String, ClientOperation, String> createNewImmutableTriple(final String key, final ClientOperation op, final String value) {
        return new ImmutableTriple<String, ClientOperation, String>(key, op, value);
    }

}
