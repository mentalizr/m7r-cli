package org.mentalizr.client.restService.accessKey;

import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCollectionSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCollectionSOX;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCreateSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCreateSOX;

public class AccessKeyCreateService extends RestService {

    private final AccessKeyCreateSO accessKeyCreateSO;

    public AccessKeyCreateService(AccessKeyCreateSO accessKeyCreateSO, RESTCallContext restCallContext) {
        super(restCallContext);
        this.accessKeyCreateSO = accessKeyCreateSO;
    }

    @Override
    public String getServiceName() {
        return "admin/user/accessKey/create";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return AccessKeyCreateSOX.toJson(this.accessKeyCreateSO);
    }

    @Override
    public String getRequestContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public AccessKeyCollectionSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String body = RestServiceCaller.call(restCallContext, this);
        return AccessKeyCollectionSOX.fromJson(body);
    }

}
