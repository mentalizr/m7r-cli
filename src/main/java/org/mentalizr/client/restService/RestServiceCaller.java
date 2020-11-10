package org.mentalizr.client.restService;

import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.config.Init;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.httpClient.HeaderHelper;
import org.mentalizr.client.httpClient.HttpClientCreator;
import org.mentalizr.client.httpClient.HttpRequestCreator;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestServiceCaller {

    public static HttpResponse call(RestService restService, CliConfiguration cliConfiguration) {

        HttpClient client = HttpClientCreator.create(cliConfiguration);
        HttpRequest httpRequest = HttpRequestCreator.create(restService, cliConfiguration);

        HttpHeaders httpHeadersRequest = httpRequest.headers();
        System.out.println("Request-Headers:");
        HeaderHelper.showHeaders(httpHeadersRequest);

        HttpResponse<String> response = null;
        // TODO ErrorHandling optimieren
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CliException("Sending request failed.", e);
        }

        HttpHeaders httpHeaderResponse = response.headers();
        System.out.println("Response-Headers:");
        HeaderHelper.showHeaders(httpHeaderResponse);

        return response;
//        System.out.println(response.statusCode());
//        System.out.println(response.body());
    }

}
