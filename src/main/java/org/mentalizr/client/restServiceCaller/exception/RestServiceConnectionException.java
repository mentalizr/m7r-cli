package org.mentalizr.client.restServiceCaller.exception;

public class RestServiceConnectionException extends RestServiceException {

    public RestServiceConnectionException(Throwable throwable) {
        super("Error on connecting server.", throwable);
    }

}
