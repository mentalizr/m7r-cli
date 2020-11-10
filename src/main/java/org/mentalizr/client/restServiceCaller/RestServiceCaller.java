package org.mentalizr.client.restServiceCaller;

import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.client.httpClient.HeaderHelper;
import org.mentalizr.client.httpClient.HttpClientCreator;
import org.mentalizr.client.httpClient.HttpRequestCreator;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestServiceCaller {

    public static String call(RestService restService, CliConfiguration cliConfiguration) throws RestServiceConnectionException, RestServiceHttpException {

        HttpClient client = HttpClientCreator.create(cliConfiguration);
        HttpRequest httpRequest = HttpRequestCreator.create(restService, cliConfiguration);

        HttpHeaders httpHeadersRequest = httpRequest.headers();
        System.out.println("Request-Headers:");
        HeaderHelper.showHeaders(httpHeadersRequest);

        HttpResponse<String> httpResponse = null;
        // TODO ErrorHandling optimieren
        try {
            httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RestServiceConnectionException(e);
        }

        HttpHeaders httpHeadersResponse = httpResponse.headers();
        System.out.println("Response-Headers:");
        HeaderHelper.showHeaders(httpHeadersResponse);

        if (httpResponse.statusCode() != 200) throw new RestServiceHttpException(httpResponse);
        return httpResponse.body();
//        System.out.println(httpResponse.statusCode());
//        System.out.println(httpResponse.body());
    }

}
