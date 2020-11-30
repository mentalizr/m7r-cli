package org.mentalizr.client.restService.accessKey;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyDeleteSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyDeleteSOX;

public class AccessKeyDeleteService extends RestService {

    private final AccessKeyDeleteSO accessKeyDeleteSO;

    public AccessKeyDeleteService(AccessKeyDeleteSO accessKeyDeleteSO, RESTCallContext restCallContext) {
        super(restCallContext);
        this.accessKeyDeleteSO = accessKeyDeleteSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/accessKey/delete";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return AccessKeyDeleteSOX.toJson(this.accessKeyDeleteSO);
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
