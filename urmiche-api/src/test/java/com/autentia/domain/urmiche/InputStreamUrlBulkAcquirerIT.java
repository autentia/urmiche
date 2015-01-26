package com.autentia.domain.urmiche;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InputStreamUrlBulkAcquirerIT {

    @Test
    public void readURLsFromFile() throws Exception {
        final InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("urls.txt");
        final InputStreamUrlBulkAcquirer acquirer = new InputStreamUrlBulkAcquirer(file);

        final List<String> urls = acquirer.acquire();

        assertThat(urls.size(), is(2));
        assertThat(urls.get(0), is("http://www.autentia.com"));
        assertThat(urls.get(1), is("http://www.adictosaltrabajo.com"));
    }
}
