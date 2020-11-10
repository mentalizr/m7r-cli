package org.mentalizr.client.restServiceCaller;

import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.httpClient.HeaderHelper;
import org.mentalizr.client.httpClient.HttpClientCreator;
import org.mentalizr.client.httpClient.HttpRequestCreator;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceCallerConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceCallerHttpException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestServiceCaller {

    public static String call(RestService restService, CliConfiguration cliConfiguration) throws RestServiceCallerConnectionException, RestServiceCallerHttpException {

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
            throw new RestServiceCallerConnectionException(e);
        }

        HttpHeaders httpHeadersResponse = httpResponse.headers();
        System.out.println("Response-Headers:");
        HeaderHelper.showHeaders(httpHeadersResponse);

        if (httpResponse.statusCode() != 200) throw new RestServiceCallerHttpException(httpResponse);
        return httpResponse.body();
//        System.out.println(httpResponse.statusCode());
//        System.out.println(httpResponse.body());
    }

}
