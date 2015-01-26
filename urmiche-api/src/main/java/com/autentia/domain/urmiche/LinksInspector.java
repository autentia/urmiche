package com.autentia.domain.urmiche;

import com.autentia.domain.urmiche.http.HttpConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LinksInspector {

    private final HttpConnectionPool httpConnectionPool;
    private List<Link> links = Collections.emptyList();

    public LinksInspector(HttpConnectionPool httpConnectionPool) { this.httpConnectionPool = httpConnectionPool; }

    public LinksInspector from(List<String> source) {
        links = source
                .parallelStream()
                .map(u -> new Link(u, httpConnectionPool.getConnection()))
                .collect(Collectors.toList());
        return this;
    }

    public LinksInspector from(InputStream source) throws IOException {
        return from(new InputStreamUrlBulkAcquirer(source).acquire());
    }

    public LinksInspector filterBy() {
        return this;
    }

    public List<Link> inspect() {
        links.parallelStream().forEach(Link::inspect);
        return links;
    }

    public List<Link> getLinks() {
        return links;
    }
}
