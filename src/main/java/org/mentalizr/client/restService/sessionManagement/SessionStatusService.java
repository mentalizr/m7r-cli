package org.mentalizr.client.restService.sessionManagement;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;

public class SessionStatusService extends RestService {

    @Override
    public String getServiceName() {
        return "sessionStatus";
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
        return ContentType.APPLICATION_JSON;
    }
}
