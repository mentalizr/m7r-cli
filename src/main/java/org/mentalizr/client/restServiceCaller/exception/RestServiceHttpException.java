package org.mentalizr.client.restServiceCaller.exception;

import java.net.http.HttpResponse;

public class RestServiceHttpException extends RestServiceException {

    private int statusCode;
    private String message;

    public RestServiceHttpException(HttpResponse httpResponse) {
        super();
        this.statusCode = httpResponse.statusCode();
    }

    public RestServiceHttpException(HttpResponse httpResponse, String message) {
        super(message);
        this.statusCode = httpResponse.statusCode();
    }

    public int getStatusCode() {
        return this.statusCode;
    }

}
