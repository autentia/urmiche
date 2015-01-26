package com.autentia.domain.urmiche;

import com.autentia.domain.urmiche.http.HttpConnection;
import com.autentia.domain.urmiche.http.HttpStatus;

import static com.autentia.domain.urmiche.http.HttpStatus.OK;
import static com.autentia.domain.urmiche.http.HttpStatus.UNKNOWN;

public class Link {

    private final HttpConnection httpConnection;
    private final String url;
    private HttpStatus statusCode = UNKNOWN;
    private Link redirection;

    Link(String url, HttpConnection httpConnection) {
        this.url = url;
        this.httpConnection = httpConnection;
    }

    void inspect() {
        httpConnection.get(url);
        statusCode = httpConnection.getStatusCode();
        if (statusCode.isRedirection()) {
            redirection = new Link(httpConnection.getRedirectHeader(), httpConnection);
            redirection.inspect();
        }
    }

    public String getUrl() { return url; }

    public HttpStatus getStatus() { return statusCode; }

    public boolean isValid() {
        if (isRedirection()) {
            return redirection.isValid();
        }
        return statusCode == OK;
    }

    public boolean isRedirection() { return redirection != null; }

    public Link getRedirection() { return redirection; }

    public String getLastUrl() {
        if (statusCode == UNKNOWN) {
            return "";
        }
        if (isRedirection()) {
            return redirection.getLastUrl();
        }
        return url;
    }
}
