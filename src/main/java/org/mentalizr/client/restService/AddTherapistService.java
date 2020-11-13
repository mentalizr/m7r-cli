package org.mentalizr.client.restService;

import org.mentalizr.cli.ContentType;
import org.mentalizr.serviceObjects.userManagement.TherapistSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class AddTherapistService extends RestService {

    private TherapistSO therapistSO;

    public AddTherapistService(TherapistSO therapistSO) {
        this.therapistSO = therapistSO;
    }

    @Override
    public String getServiceName() {
        return "addTherapist";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getBody() {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(this.therapistSO);
    }

    @Override
    public String getContentType() {
        return ContentType.APPLICATION_JSON;
    }
}
