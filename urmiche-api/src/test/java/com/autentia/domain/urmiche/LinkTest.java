package com.autentia.domain.urmiche;

import com.autentia.domain.urmiche.http.HttpConnection;
import com.autentia.domain.urmiche.http.HttpStatus;
import org.junit.Test;

import static com.autentia.domain.urmiche.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinkTest {

    public static final String DUMMY_URL = "http://www.autentia.com";
    public static final boolean IS_VALID = true;
    public static final boolean IS_REDIRECTION = true;
    public static final boolean NOT_VALID = false;
    public static final boolean NOT_REDIRECTION = false;

    private final HttpConnection stubHttpConnection = mock(HttpConnection.class);
    private final Link link = new Link(DUMMY_URL, stubHttpConnection);

    @Test
    public void valid_link() throws Exception {
        when(stubHttpConnection.getStatusCode()).thenReturn(OK);

        link.inspect();

        assertThatLinkIs(link, DUMMY_URL, IS_VALID, NOT_REDIRECTION, OK);
    }

    @Test
    public void broken_link() throws Exception {
        when(stubHttpConnection.getStatusCode()).thenReturn(NOT_FOUND);

        link.inspect();

        assertThatLinkIs(link, DUMMY_URL, NOT_VALID, NOT_REDIRECTION, NOT_FOUND);
    }

    @Test
    public void malformed_link() throws Exception {
        when(stubHttpConnection.getStatusCode()).thenReturn(MALFORMED);

        link.inspect();

        assertThatLinkIs(link, DUMMY_URL, NOT_VALID, NOT_REDIRECTION, MALFORMED);
    }

    @Test
    public void redirect_link() throws Exception {
        when(stubHttpConnection.getStatusCode()).thenReturn(MOVED_PERMANENTLY).thenReturn(MOVED_TEMPORARILY).thenReturn(OK);
        when(stubHttpConnection.getRedirectHeader()).thenReturn("http://second-url").thenReturn("http://last-url");

        link.inspect();

        assertThatLinkIs(link, DUMMY_URL, IS_VALID, IS_REDIRECTION, MOVED_PERMANENTLY);

        final Link firstRedirection = link.getRedirection();
        assertThatLinkIs(firstRedirection, "http://second-url", IS_VALID, IS_REDIRECTION, MOVED_TEMPORARILY);

        final Link secondRedirection = firstRedirection.getRedirection();
        assertThatLinkIs(secondRedirection, "http://last-url", IS_VALID, NOT_REDIRECTION, OK);
    }

    private void assertThatLinkIs(Link link, String expectedUrl, boolean expectedValid, boolean expectedIsRedirection, HttpStatus expectedStatus) {
        assertThat(link.getUrl(), is(expectedUrl));
        assertThat(link.isValid(), is(expectedValid));
        assertThat(link.isRedirection(), is(expectedIsRedirection));
        assertThat(link.getStatus(), is(expectedStatus));
    }

}
