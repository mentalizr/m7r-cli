package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientAddSO;
import org.mentalizr.serviceObjects.userManagement.PatientAddSOX;

public class PatientAddService extends RestService {

    private final PatientAddSO patientAddSO;

    public PatientAddService(PatientAddSO patientAddSO, RESTCallContext restCallContext) {
        super(restCallContext);
        this.patientAddSO = patientAddSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/patient/add";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return PatientAddSOX.toJson(this.patientAddSO);
    }

    @Override
    public String getRequestContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public PatientAddSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String body = RestServiceCaller.call(restCallContext, this);
        return PatientAddSOX.fromJson(body);
    }

}
