package org.mentalizr.client.restServiceCaller.exception;

public class RestServiceCallerConnectionException extends RestServiceCallerException {

    public RestServiceCallerConnectionException(Throwable throwable) {
        super("Error on connecting server.", throwable);
    }

}
