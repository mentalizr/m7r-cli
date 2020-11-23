package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSOX;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class TherapistAddService extends RestService {

    private final TherapistAddSO therapistAddSO;

    public TherapistAddService(TherapistAddSO therapistAddSO) {
        this.therapistAddSO = therapistAddSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/add";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getBody() {
        return TherapistAddSOX.toJson(this.therapistAddSO);
    }

    @Override
    public String getContentType() {
        return ContentType.APPLICATION_JSON;
    }
}
