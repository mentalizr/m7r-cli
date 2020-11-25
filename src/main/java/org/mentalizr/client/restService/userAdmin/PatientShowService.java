package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreCollectionSOX;

public class PatientShowService extends RestService {

    public PatientShowService(RESTCallContext restCallContext) {
        super(restCallContext);
    }

    @Override
    public String getServiceName() {
        return "admin/user/patient/getAll";
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
    public PatientRestoreCollectionSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String body = RestServiceCaller.call(restCallContext, this);
        return PatientRestoreCollectionSOX.fromJson(body);
    }

}
