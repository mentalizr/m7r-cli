package org.mentalizr.client.restServiceCaller.exception;

public class RestServiceCallerException extends Exception {

    public RestServiceCallerException() {
    }

    public RestServiceCallerException(String message) {
        super(message);
    }

    public RestServiceCallerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestServiceCallerException(Throwable cause) {
        super(cause);
    }

    public RestServiceCallerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
