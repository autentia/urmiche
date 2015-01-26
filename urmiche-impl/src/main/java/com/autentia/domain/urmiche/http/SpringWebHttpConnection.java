package com.autentia.domain.urmiche.http;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import static com.autentia.domain.urmiche.http.HttpStatus.MALFORMED;
import static com.autentia.domain.urmiche.http.HttpStatus.NOT_FOUND;

public class SpringWebHttpConnection implements HttpConnection {

    private final RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory() {
        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            super.prepareConnection(connection, httpMethod);
            connection.setInstanceFollowRedirects(false); // Don't follow redirects (301, 302, ...)
        }
    });

    private HttpStatus status;
    private ResponseEntity<String> entity;

    @Override
    public void get(String url) {
        status = MALFORMED;
        try {
            entity = restTemplate.getForEntity(url, String.class);
            status = HttpStatus.valueOf(entity.getStatusCode().value());

        } catch (HttpClientErrorException e) {
            status = HttpStatus.valueOf(e.getStatusCode().value());

        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof UnknownHostException) {
                status = NOT_FOUND;
            }

        } catch (Exception ignored) {
            // Any other exception is considered as a malformed URL
        }
    }

    @Override
    public HttpStatus getStatusCode() {
        return status;
    }

    @Override
    public String getRedirectHeader() {
        return entity.getHeaders().getLocation().toString();
    }

}
