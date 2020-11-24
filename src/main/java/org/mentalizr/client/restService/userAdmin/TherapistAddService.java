package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSOX;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class TherapistAddService extends RestService {

    private final TherapistAddSO therapistAddSO;

    public TherapistAddService(TherapistAddSO therapistAddSO, RESTCallContext restCallContext) {
        super(restCallContext);
        this.therapistAddSO = therapistAddSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/add";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return TherapistAddSOX.toJson(this.therapistAddSO);
    }

    @Override
    public String getRequestContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public TherapistAddSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String body = RestServiceCaller.call(restCallContext, this);
        return TherapistAddSOX.fromJson(body);
    }

}
