package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class ProgramDeleteService extends RestService {

    private final String programId;

    public ProgramDeleteService(String programId, RESTCallContext restCallContext) {
        super(restCallContext);
        this.programId = programId;
    }

    @Override
    public String getServiceName() {
        return "admin/user/program/delete/" + this.programId;
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
        return null;
    }

    @Override
    public Object call() throws RestServiceHttpException, RestServiceConnectionException {
        RestServiceCaller.call(restCallContext, this);
        return null;
    }

}
