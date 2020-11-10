package org.mentalizr.client.restServiceCaller.exception;

public class RestServiceException extends Exception {

    public RestServiceException() {
    }

    public RestServiceException(String message) {
        super(message);
    }

    public RestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestServiceException(Throwable cause) {
        super(cause);
    }

    public RestServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
