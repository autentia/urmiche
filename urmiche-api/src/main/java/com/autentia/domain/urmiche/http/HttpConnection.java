package com.autentia.domain.urmiche.http;

public interface HttpConnection {

    void get(String url);

    HttpStatus getStatusCode();

    String getRedirectHeader();
}
