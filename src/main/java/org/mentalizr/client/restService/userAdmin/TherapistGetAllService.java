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
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSOX;

public class TherapistGetAllService extends RestService {

    public TherapistGetAllService(RESTCallContext restCallContext) {
        super(restCallContext);
    }

    @Override
    public String getServiceName() {
        return "admin/user/therapist/getAll";
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
    public TherapistRestoreCollectionSO call() throws RestServiceHttpException, RestServiceConnectionException {
        String body = RestServiceCaller.call(restCallContext, this);
        return TherapistRestoreCollectionSOX.fromJson(body);
    }

    public static TherapistRestoreCollectionSO call(CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        return new TherapistGetAllService(restCallContext).call();
    }

}
