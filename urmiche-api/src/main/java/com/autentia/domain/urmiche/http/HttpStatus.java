package com.autentia.domain.urmiche.http;

public enum HttpStatus {
    UNKNOWN(-2),
    MALFORMED(-1),
    OK(200),
    MOVED_PERMANENTLY(301),
    MOVED_TEMPORARILY(302),
    NOT_FOUND(404);

    private final int code;
    private boolean redirection;

    private HttpStatus(int code) {
        this.code = code;
    }

    public static HttpStatus valueOf(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.code == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for: " + statusCode);
    }

    public boolean isRedirection() {
        return this == MOVED_PERMANENTLY || this == MOVED_TEMPORARILY;
    }
}
