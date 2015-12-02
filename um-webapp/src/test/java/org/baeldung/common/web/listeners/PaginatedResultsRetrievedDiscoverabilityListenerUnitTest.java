package org.baeldung.common.web.listeners;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

import org.baeldung.common.util.LinkUtil;
import org.baeldung.common.web.events.PaginatedResultsRetrievedEvent;
import org.baeldung.um.web.dto.UserDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.net.HttpHeaders;

@RunWith(MockitoJUnitRunner.class)
@Ignore("in progress")
public class PaginatedResultsRetrievedDiscoverabilityListenerUnitTest {

    private static final Class<UserDto> RESOURCE_CLASS = UserDto.class;

    private static final int PAGE_SIZE_TO_SET = 10;

    private static final String RESOURCE_HTTP_LOCATION = "http://example.com/context/api/resource";

    private PaginatedResultsRetrievedDiscoverabilityListener listener;

    @Mock
    private HttpServletResponse httpServletResponse;

    private UriComponentsBuilder uriComponentsBuilder;

    @Before
    public final void before() {
        listener = new PaginatedResultsRetrievedDiscoverabilityListener();
        uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(RESOURCE_HTTP_LOCATION);
    }

    // tests

    @Test
    public final void givenOnlyOnePage_whenNotifiedToAddLinkForFirstPage_thenAddNoLinksAdded() {
        // given
        final int pageToSet = 0;
        final int totalPagesToSet = 1;

        // when
        listener.onApplicationEvent(new PaginatedResultsRetrievedEvent<UserDto>(RESOURCE_CLASS, uriComponentsBuilder, httpServletResponse, pageToSet, totalPagesToSet, PAGE_SIZE_TO_SET));

        // then
        verify(httpServletResponse, never()).addHeader(eq(HttpHeaders.LINK), anyString());
    }

    @Test
    public final void givenThreePages_whenNotifierToAddLinkForFirstPage_thenNextAndLastLinksAreAdded() {
        // given
        final int pageToSet = 0;
        final int totalPagesToSet = 3;

        // when
        listener.onApplicationEvent(new PaginatedResultsRetrievedEvent<UserDto>(RESOURCE_CLASS, uriComponentsBuilder, httpServletResponse, pageToSet, totalPagesToSet, PAGE_SIZE_TO_SET));

        // then
        verify(httpServletResponse).addHeader(eq(HttpHeaders.LINK), eq(LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=" + (pageToSet + 1) + "&size=" + PAGE_SIZE_TO_SET, "next") + ", "
                + LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=" + (totalPagesToSet - 1) + "&size=" + PAGE_SIZE_TO_SET, "last")));
    }

    @Test
    public final void givenThreePages_whenNotifiedToAddLinkForThirdPage_thenPreviousAndFirstLinksAreAdded() {
        // given
        final int pageToSet = 2;
        final int totalPagesToSet = 3;

        // when
        listener.onApplicationEvent(new PaginatedResultsRetrievedEvent<UserDto>(RESOURCE_CLASS, uriComponentsBuilder, httpServletResponse, pageToSet, totalPagesToSet, PAGE_SIZE_TO_SET));

        // then
        verify(httpServletResponse).addHeader(eq(HttpHeaders.LINK), eq(LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=" + (pageToSet - 1) + "&size=" + PAGE_SIZE_TO_SET, "prev") + ", "
                + LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=0&size=" + PAGE_SIZE_TO_SET, "first")));
    }

    @Test
    public final void givenThreePages_whenNotifiedToAddLinkForSecondPage_thenAllLinksAreAdded() {
        // given
        final int pageToSet = 1;
        final int totalPagesToSet = 3;

        // when
        listener.onApplicationEvent(new PaginatedResultsRetrievedEvent<UserDto>(RESOURCE_CLASS, uriComponentsBuilder, httpServletResponse, pageToSet, totalPagesToSet, PAGE_SIZE_TO_SET));

        // then
        verify(httpServletResponse).addHeader(eq(HttpHeaders.LINK),
                eq(LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=" + (pageToSet + 1) + "&size=" + PAGE_SIZE_TO_SET, "next") + ", "
                        + LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=" + (pageToSet - 1) + "&size=" + PAGE_SIZE_TO_SET, "prev") + ", "
                        + LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=0&size=" + PAGE_SIZE_TO_SET, "first") + ", "
                        + LinkUtil.createLinkHeader(RESOURCE_HTTP_LOCATION + "/" + RESOURCE_CLASS.getSimpleName().toLowerCase() + "?page=" + (totalPagesToSet - 1) + "&size=" + PAGE_SIZE_TO_SET, "last")));

    }

}