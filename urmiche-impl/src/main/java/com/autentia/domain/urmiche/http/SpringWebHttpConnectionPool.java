package com.autentia.domain.urmiche.http;

import org.springframework.stereotype.Service;

@Service(value = "httpConnectionPool")
public class SpringWebHttpConnectionPool implements HttpConnectionPool {

    @Override
    public HttpConnection getConnection() {
        return new SpringWebHttpConnection();
    }

}
