package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class ShowTherapistService extends RestService {

    public ShowTherapistService() {
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/getAll";
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
