package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;

public class TherapistGetService extends RestService {

    private final String username;

    public TherapistGetService(String username) {
        this.username = username;
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/get/" + this.username;
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
