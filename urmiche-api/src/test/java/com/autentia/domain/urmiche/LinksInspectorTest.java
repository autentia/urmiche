package com.autentia.domain.urmiche;

import com.autentia.domain.urmiche.http.HttpConnection;
import com.autentia.domain.urmiche.http.HttpConnectionPool;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.autentia.domain.urmiche.http.HttpStatus.OK;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class LinksInspectorTest {

    private final static List<String> URLS = Arrays.asList("http://first-url", "http://first-second", "http://first-last");

    private final HttpConnectionPool spyHttpConnectionPool = mock(HttpConnectionPool.class);

    private final LinksInspector linksInspector = new LinksInspector(spyHttpConnectionPool);

    public LinksInspectorTest() {
        final HttpConnection stubHttpConnection = mock(HttpConnection.class);
        when(stubHttpConnection.getStatusCode()).thenReturn(OK);

        when(spyHttpConnectionPool.getConnection()).thenReturn(stubHttpConnection);
    }

    @Test
    public void call_all_urls() throws Exception {

        final List<Link> links = linksInspector.from(URLS).inspect();

        assertThat(links.size(), is(URLS.size()));
        verify(spyHttpConnectionPool, times(URLS.size())).getConnection();
    }

}
