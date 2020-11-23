package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.serviceObjects.userManagement.ProgramSO;
import org.mentalizr.serviceObjects.userManagement.ProgramSOX;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class ProgramAddService extends RestService {

    private final ProgramSO programSO;

    public ProgramAddService(ProgramSO programSO) {
        this.programSO = programSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/program/create";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getBody() {
        return ProgramSOX.toJson(this.programSO);
    }

    @Override
    public String getContentType() {
        return ContentType.APPLICATION_JSON;
    }

}
