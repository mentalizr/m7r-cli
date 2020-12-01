package org.mentalizr.client.restService.accessKey;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ContentType;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.util.List;

public class AccessKeyGetAllService extends RestService {

    public AccessKeyGetAllService(RESTCallContext restCallContext) {
        super(restCallContext);
    }

    @Override
    public String getServiceName() {
        return "admin/user/accessKey/getAll";
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
    public AccessKeyCollectionSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String body = RestServiceCaller.call(restCallContext, this);
        return AccessKeyCollectionSOX.fromJson(body);
    }

    public static AccessKeyCollectionSO call(CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        return new AccessKeyGetAllService(restCallContext).call();
    }

}
