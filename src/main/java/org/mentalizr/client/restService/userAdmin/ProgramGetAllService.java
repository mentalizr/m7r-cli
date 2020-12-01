package org.mentalizr.client.restService.userAdmin;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ContentType;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.ProgramCollectionSO;
import org.mentalizr.serviceObjects.userManagement.ProgramCollectionSOX;
import org.mentalizr.serviceObjects.userManagement.ProgramSO;

import java.util.List;

public class ProgramGetAllService extends RestService {

    public ProgramGetAllService(RESTCallContext restCallContext) {
        super(restCallContext);
    }

    @Override
    public String getServiceName() {
        return "admin/user/program/getAll";
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
    public ProgramCollectionSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String body = RestServiceCaller.call(restCallContext, this);
        return ProgramCollectionSOX.fromJson(body);
    }

    public static ProgramCollectionSO call(CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        return new ProgramGetAllService(restCallContext).call();
    }

}
