package org.rest.common.client.template;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rest.common.client.template.AbstractClientRESTTemplate;
import org.rest.common.util.SearchCommonUtil;
import org.rest.sec.client.template.newer.UserClientRESTTemplate;

public class URIUnitTest {

    @SuppressWarnings("rawtypes")
    public static AbstractClientRESTTemplate instance;

    @BeforeClass
    public static void beforeClass() {
        instance = new UserClientRESTTemplate();
    }

    @Test(expected = NullPointerException.class)
    public void givenNullArgumentArray_whenURIIsConstructed_thenException() {
        AbstractClientRESTTemplate.constructURI((String[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSingleAttributeInArray_whenURIIsConstructed_thenException() {
        AbstractClientRESTTemplate.constructURI(new String[] { "Single" });
    }

    @Test
    public void givenTwoAttributesInArray_whenURIIsConstructed_thenNoExceptions() {
        AbstractClientRESTTemplate.constructURI(new String[] { "One", "Two" });
    }

    @Test
    public void whenURIIsConstructed_thenResultIsNonNull() {
        assertNotNull(AbstractClientRESTTemplate.constructURI(new String[] { "One", "Two" }));
    }

    @Test
    public void whenURIIsConstructed_thenResultIsCorrect() {
        final String uri = AbstractClientRESTTemplate.constructURI(new String[] { "key", "value" });
        assertThat(uri, equalTo("key=value"));
    }

    @Test
    public void givenTwoParameters_whenURIIsConstructed_thenResultIsCorrect() {
        final String uri = AbstractClientRESTTemplate.constructURI(new String[] { "key1", "value1", "key2", "value2" });
        assertThat(uri, equalTo("key1=value1" + SearchCommonUtil.SEPARATOR + "key2=value2"));
    }

}
