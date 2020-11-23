package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;

public class ProgramShowService extends RestService {

    public ProgramShowService() {
    }

    @Override
    public String getServiceName() {
        return "admin/user/program/getAll";
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
