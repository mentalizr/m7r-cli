package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class RestoreTherapistService extends RestService {

    private TherapistRestoreSO therapistRestoreSO;

    public RestoreTherapistService(TherapistRestoreSO therapistRestoreSO) {
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
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(this.therapistRestoreSO);
    }

    @Override
    public String getContentType() {
        return ContentType.APPLICATION_JSON;
    }
}
