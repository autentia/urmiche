package com.autentia.domain.urmiche.http;

import org.junit.Test;

import static com.autentia.domain.urmiche.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpConnectionIT {

    private final HttpConnection httpConnection = new SpringWebHttpConnection();

    @Test
    public void valid_link() throws Exception {
        httpConnection.get("http://www.marca.com");

        assertThat(httpConnection.getStatusCode(), is(OK));
    }

    @Test
    public void broken_link() throws Exception {
        httpConnection.get("http://www.google.com/link-does-not-exist");

        assertThat(httpConnection.getStatusCode(), is(NOT_FOUND));
    }

    @Test
    public void malformed_link() throws Exception {
        httpConnection.get("This is not a url");

        assertThat(httpConnection.getStatusCode(), is(MALFORMED));
    }

    @Test
    public void moved_permanently_link() throws Exception {
        httpConnection.get("http://marca.es");

        assertThat(httpConnection.getStatusCode(), is(MOVED_PERMANENTLY));
        assertThat(httpConnection.getRedirectHeader(), is("http://www.marca.com/"));
    }

    @Test
    public void moved_temporarily_link() throws Exception {
        httpConnection.get("http://google.com");

        assertThat(httpConnection.getStatusCode(), is(MOVED_TEMPORARILY));
    }

}
