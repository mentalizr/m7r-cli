package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSOX;

public class PatientGetService extends RestService {

    private final String username;

    public PatientGetService(String username, RESTCallContext restCallContext) {
        super(restCallContext);
        this.username = username;
    }

    @Override
    public String getServiceName() {
        return "admin/user/patient/get/" + this.username;
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
    public PatientRestoreSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String responseBody = RestServiceCaller.call(restCallContext, this);
        return PatientRestoreSOX.fromJson(responseBody);
    }

}
