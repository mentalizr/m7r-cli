package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

public class TherapistGetService extends RestService {

    private final String username;

    public TherapistGetService(String username, RESTCallContext restCallContext) {
        super(restCallContext);
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
    public String getRequestBody() {
        return null;
    }

    @Override
    public String getRequestContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public TherapistRestoreSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String responseBody = RestServiceCaller.call(restCallContext, this);
        return TherapistRestoreSOX.fromJson(responseBody);
    }

}
