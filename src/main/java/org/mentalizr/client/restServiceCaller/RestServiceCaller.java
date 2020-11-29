package org.mentalizr.client.restServiceCaller;

import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.httpClient.HeaderHelper;
import org.mentalizr.client.httpClient.HttpClientCreator;
import org.mentalizr.client.httpClient.HttpRequestCreator;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceBusinessException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceServerException;
import org.mentalizr.serviceObjects.ErrorSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestServiceCaller {

    public static String call(
            RESTCallContext restCallContext,
            RestService restService
    ) throws RestServiceConnectionException, RestServiceHttpException {

        AssertMethodPrecondition.parameterNotNull("restCallContext", restCallContext);
        AssertMethodPrecondition.parameterNotNull("restService", restService);

        HttpClient client = HttpClientCreator.create(restCallContext);
        HttpRequest httpRequest = HttpRequestCreator.create(restService, restCallContext);

        if (restCallContext.isDebug()) HeaderHelper.showRequestHeaders(httpRequest);

        HttpResponse<String> httpResponse;
        try {
            httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RestServiceConnectionException(e);
        }

        if (restCallContext.isDebug()) HeaderHelper.showReponseHeaders(httpResponse);

        if (restCallContext.isDebug()) System.out.println("response-body: " + httpResponse.body());

        if (httpResponse.statusCode() == 200) {
            return httpResponse.body();
        } else if (httpResponse.statusCode() == 401) {
            throw new RestServiceBusinessException(httpResponse, "No valid session.");
        } else if (httpResponse.statusCode() == 404) {
            throw new RestServiceBusinessException(httpResponse, "Backend service not found.");
        } else if (httpResponse.statusCode() > 470 && httpResponse.statusCode() < 500) {
            String responseBody = httpResponse.body();
            if (Strings.isNotNullAndNotEmpty(responseBody)) {
                Jsonb jsonb = JsonbBuilder.create();
                ErrorSO errorSO = jsonb.fromJson(responseBody, ErrorSO.class);
                throw new RestServiceBusinessException(httpResponse, errorSO.getMessage());
            }
            throw new RestServiceBusinessException(httpResponse);
        } else if (httpResponse.statusCode() == 500) {
            String responseBody = httpResponse.body();
            if (Strings.isNotNullAndNotEmpty(responseBody)) {
                Jsonb jsonb = JsonbBuilder.create();
                ErrorSO errorSO = jsonb.fromJson(responseBody, ErrorSO.class);
                throw new RestServiceServerException(httpResponse, errorSO.getMessage());
            }
            throw new RestServiceServerException(httpResponse);
        } else {
            throw new RestServiceHttpException(httpResponse);
        }

    }


}
