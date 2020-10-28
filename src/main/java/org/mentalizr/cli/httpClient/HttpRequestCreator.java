package org.mentalizr.cli.httpClient;

import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.restService.HttpMethod;
import org.mentalizr.cli.restService.RestService;
import org.mentalizr.cli.restService.RestServiceHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Base64;

public class HttpRequestCreator {

    public static HttpRequest create(RestService restService, CliConfiguration cliConfiguration) {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

        addUriToRequest(requestBuilder, restService, cliConfiguration);
        addHttpMethodToRequest(requestBuilder, restService);
        addContentTypeHeader(requestBuilder, restService);
        setProxyServerCredentials(requestBuilder, cliConfiguration);

        return requestBuilder.build();
    }

    public static void addUriToRequest(HttpRequest.Builder httpRequestBuilder, RestService restService, CliConfiguration cliConfiguration) {

        String uri = RestServiceHelper.getServiceUrl(restService, cliConfiguration);

        System.out.println("Service: " + uri);

        try {
            httpRequestBuilder.uri(new URI(uri));
        } catch (URISyntaxException e) {
            throw new CliException(e);
        }

    }

    public static void addHttpMethodToRequest(HttpRequest.Builder httpRequestBuilder, RestService restService) {

        HttpMethod httpMethod = restService.getMethod();

        if (httpMethod == HttpMethod.GET) {

            httpRequestBuilder.GET();

            System.out.println("Method: GET");

        } else if (httpMethod == HttpMethod.POST) {

            String body = restService.getBody();
            httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));

            System.out.println("Method: POST");
            System.out.println("Body: " + body);

        } else {
            throw new RuntimeException("Unknown HttpMethod: " + httpMethod.name());
        }
    }

    public static void addContentTypeHeader(HttpRequest.Builder httpRequestBuilder, RestService restService) {
        if (restService.hasContentType()) {
            httpRequestBuilder.header("Content-Type", restService.getContentType());
        }
    }

    private static void setProxyServerCredentials(HttpRequest.Builder httpRequestBuilder, CliConfiguration cliConfiguration) {

        if (!cliConfiguration.hasProxyServerCredentials()) return;

        String loginString = cliConfiguration.getProxyServerUser() + ":" + cliConfiguration.getProxyServerPassword();
        String encoded = new String(Base64.getEncoder().encode(loginString.getBytes()));
        httpRequestBuilder.setHeader("Proxy-Authorization", "Basic " + encoded);

    }

}
