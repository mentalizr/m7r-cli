package org.mentalizr.client.restService;

import org.mentalizr.cli.ContentType;

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
