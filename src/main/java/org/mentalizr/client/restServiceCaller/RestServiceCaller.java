package org.mentalizr.client.restServiceCaller;

import org.mentalizr.client.ClientConfiguration;
import org.mentalizr.client.ClientContext;
import org.mentalizr.client.httpClient.HeaderHelper;
import org.mentalizr.client.httpClient.HttpClientCreator;
import org.mentalizr.client.httpClient.HttpRequestCreator;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestServiceCaller {

    public static String call(RestService restService, ClientContext clientContext) throws RestServiceConnectionException, RestServiceHttpException {

        ClientConfiguration clientConfiguration = clientContext.getClientConfiguration();
        HttpClient client = HttpClientCreator.create(clientConfiguration);
        HttpRequest httpRequest = HttpRequestCreator.create(restService, clientConfiguration);

        if (clientContext.isDebug()) HeaderHelper.showRequestHeaders(httpRequest);

        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RestServiceConnectionException(e);
        }

        if (clientContext.isDebug()) HeaderHelper.showReponseHeaders(httpResponse);

        if (httpResponse.statusCode() != 200) throw new RestServiceHttpException(httpResponse);
        return httpResponse.body();
    }

}
