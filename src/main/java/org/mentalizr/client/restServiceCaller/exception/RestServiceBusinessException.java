package org.mentalizr.client.restServiceCaller.exception;

import java.net.http.HttpResponse;

public class RestServiceBusinessException extends RestServiceHttpException {

    public RestServiceBusinessException(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public RestServiceBusinessException(HttpResponse httpResponse, String message) {
        super(httpResponse, message);
    }

}
