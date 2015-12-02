package org.baeldung.common.web;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class UriParsingUnitTest {

    @Test
    public final void whenURIIsParsed_thenResultIsCorrect() {
        final String uri = "http://localhost:8080/rest-sec/api/privilege?q=name%3DjDiedXRD";

        // When
        final UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();

        // Then
        assertTrue(uriComponents.getQueryParams().size() == 1);
    }

}
