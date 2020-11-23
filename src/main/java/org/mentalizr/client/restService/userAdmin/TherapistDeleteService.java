package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;

public class TherapistDeleteService extends RestService {

    private final String username;

    public TherapistDeleteService(String username) {
        this.username = username;
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/delete/" + this.username;
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
