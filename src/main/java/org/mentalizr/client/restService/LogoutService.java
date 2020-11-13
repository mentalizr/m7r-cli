package org.mentalizr.client.restService;

import org.mentalizr.client.RESTCallContext;

public class LogoutService extends RestService {

    public LogoutService() {
    }

    @Override
    public String getServiceName() {
        return "logout";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

}
