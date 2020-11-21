package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;

public class DeleteTherapistService extends RestService {

    private String username;

    public DeleteTherapistService(String username) {
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
