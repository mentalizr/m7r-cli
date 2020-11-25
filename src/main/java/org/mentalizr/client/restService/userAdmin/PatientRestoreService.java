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
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

public class PatientRestoreService extends RestService {

    private final PatientRestoreSO patientRestoreSO;

    public PatientRestoreService(PatientRestoreSO patientRestoreSO, RESTCallContext restCallContext) {
        super(restCallContext);
        this.patientRestoreSO = patientRestoreSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/patient/restore";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return PatientRestoreSOX.toJson(this.patientRestoreSO);
    }

    @Override
    public String getRequestContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Object call() throws RestServiceHttpException, RestServiceConnectionException {
        RestServiceCaller.call(restCallContext, this);
        return null;
    }
}
