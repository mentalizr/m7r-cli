package org.mentalizr.client.restService.accessKey;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSOX;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSOX;

public class AccessKeyRestoreService extends RestService {

    private final AccessKeyRestoreSO accessKeyRestoreSO;

    public AccessKeyRestoreService(AccessKeyRestoreSO accessKeyRestoreSO, RESTCallContext restCallContext) {
        super(restCallContext);
        this.accessKeyRestoreSO = accessKeyRestoreSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/accessKey/restore";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return AccessKeyRestoreSOX.toJson(this.accessKeyRestoreSO);
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
