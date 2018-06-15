package com.baeldung.common.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.baeldung.common.util.LinkUtil;

public final class LinkUtilTest {

    // tests

    @Test
    public final void whenGatheringLinks_thenNoException() {
        LinkUtil.gatherLinkHeaders("");
    }

    @Test
    public final void whenGatheringOneLink_thenResultIsCorrect() {
        final String uri = randomAlphabetic(6);
        final String linkHeader = LinkUtil.createLinkHeader(uri, LinkUtil.REL_COLLECTION);

        // When
        final String links = LinkUtil.gatherLinkHeaders(linkHeader);

        // Then
        assertThat(links, equalTo(linkHeader));
    }

    @Test
    public final void whenGatheringTwoLink_thenResultIsCorrect() {
        final String uri1 = randomAlphabetic(6);
        final String uri2 = randomAlphabetic(6);
        String linkHeader1 = LinkUtil.createLinkHeader(uri1, LinkUtil.REL_COLLECTION);
        String linkHeader2 = LinkUtil.createLinkHeader(uri2, LinkUtil.REL_COLLECTION);

        // When
        final String links = LinkUtil.gatherLinkHeaders(linkHeader1, linkHeader2);

        // Then
        assertNotNull(links);
    }

}
