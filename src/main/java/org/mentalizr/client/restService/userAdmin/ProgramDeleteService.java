package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;

public class ProgramDeleteService extends RestService {

    private final String programId;

    public ProgramDeleteService(String programId) {
        this.programId = programId;
    }

    @Override
    public String getServiceName() {
        return "admin/user/program/delete/" + this.programId;
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
