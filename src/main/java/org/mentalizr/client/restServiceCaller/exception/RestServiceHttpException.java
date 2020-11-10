package org.mentalizr.client.restServiceCaller.exception;

import java.net.http.HttpResponse;

public class RestServiceHttpException extends RestServiceException {

    private int statusCode;

    public RestServiceHttpException(HttpResponse httpResponse) {
        super();
        this.statusCode = httpResponse.statusCode();
    }

    public int getStatusCode() {
        return this.statusCode;
    }

}
