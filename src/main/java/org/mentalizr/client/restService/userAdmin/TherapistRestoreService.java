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

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class TherapistRestoreService extends RestService {

    private final TherapistRestoreSO therapistRestoreSO;

    public TherapistRestoreService(TherapistRestoreSO therapistRestoreSO, RESTCallContext restCallContext) {
        super(restCallContext);
        this.therapistRestoreSO = therapistRestoreSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/restore";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return TherapistRestoreSOX.toJson(this.therapistRestoreSO);
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
