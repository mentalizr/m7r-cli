package org.mentalizr.client.restService.sessionManagement;

import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;

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
