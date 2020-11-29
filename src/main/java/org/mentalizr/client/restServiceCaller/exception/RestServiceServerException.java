package org.mentalizr.client.restServiceCaller.exception;

import java.net.http.HttpResponse;

public class RestServiceServerException extends RestServiceHttpException {

    public RestServiceServerException(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public RestServiceServerException(HttpResponse httpResponse, String message) {
        super(httpResponse, message);
    }

}
