package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class PatientDeleteService extends RestService {

    private final String username;

    public PatientDeleteService(String username, RESTCallContext restCallContext) {
        super(restCallContext);
        this.username = username;
    }

    @Override
    public String getServiceName() {
        return "admin/user/patient/delete/" + this.username;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public String getRequestContentType() {
        return null;
    }

    @Override
    public Object call() throws RestServiceHttpException, RestServiceConnectionException {
        RestServiceCaller.call(restCallContext, this);
        return null;
    }

}
