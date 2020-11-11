package org.mentalizr.client.restService;

public class LogoutService extends RestService {

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
