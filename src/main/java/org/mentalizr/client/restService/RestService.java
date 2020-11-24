package org.mentalizr.client.restService;

import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public abstract class RestService {

    protected RESTCallContext restCallContext;

    public RestService(RESTCallContext restCallContext) {
        this.restCallContext = restCallContext;
    }

    public abstract String getServiceName();

    public abstract HttpMethod getMethod();

    public abstract String getRequestBody();

    public abstract String getRequestContentType();

    public boolean hasContentType() {
        return Strings.isNotNullAndNotEmpty(this.getRequestContentType());
    }

    public abstract Object call() throws RestServiceHttpException, RestServiceConnectionException;

}
