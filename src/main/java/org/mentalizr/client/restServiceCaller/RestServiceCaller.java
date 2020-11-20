package org.mentalizr.client.restServiceCaller;

import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.httpClient.HeaderHelper;
import org.mentalizr.client.httpClient.HttpClientCreator;
import org.mentalizr.client.httpClient.HttpRequestCreator;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.ErrorSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestServiceCaller {

    public static String call(RESTCallContext restCallContext, RestService restService) throws RestServiceConnectionException, RestServiceHttpException {

        HttpClient client = HttpClientCreator.create(restCallContext);
        HttpRequest httpRequest = HttpRequestCreator.create(restService, restCallContext);

        if (restCallContext.isDebug()) HeaderHelper.showRequestHeaders(httpRequest);

        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RestServiceConnectionException(e);
        }

        if (restCallContext.isDebug()) HeaderHelper.showReponseHeaders(httpResponse);

        if (restCallContext.isDebug()) System.out.println("response-body: " + httpResponse.body());

        if (httpResponse.statusCode() != 200) {
            String responseBody = httpResponse.body();
            if (Strings.isNotNullAndNotEmpty(responseBody)) {
                Jsonb jsonb = JsonbBuilder.create();
                ErrorSO errorSO = jsonb.fromJson(responseBody, ErrorSO.class);
                throw new RestServiceHttpException(httpResponse, errorSO.getMessage());
            }
            throw new RestServiceHttpException(httpResponse);
        }
        return httpResponse.body();
    }

}
