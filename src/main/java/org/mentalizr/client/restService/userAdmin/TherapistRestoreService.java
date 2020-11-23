package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class TherapistRestoreService extends RestService {

    private final TherapistRestoreSO therapistRestoreSO;

    public TherapistRestoreService(TherapistRestoreSO therapistRestoreSO) {
        this.therapistRestoreSO = therapistRestoreSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/restore";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getBody() {
        return TherapistRestoreSOX.toJson(this.therapistRestoreSO);
    }

    @Override
    public String getContentType() {
        return ContentType.APPLICATION_JSON;
    }
}
