package org.example.exception;

public class URLNotFoundException extends RuntimeException {
    public URLNotFoundException(String message) {
        super(message);
    }
}
