package org.mentalizr.client.restServiceCaller.exception;

import java.net.http.HttpResponse;

public class RestServiceCallerHttpException extends RestServiceCallerException {

    private int statusCode;

    public RestServiceCallerHttpException(HttpResponse httpResponse) {
        super();
        this.statusCode = httpResponse.statusCode();
    }

    public int getStatusCode() {
        return this.statusCode;
    }

}
