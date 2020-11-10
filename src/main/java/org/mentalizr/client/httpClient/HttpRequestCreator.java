package org.mentalizr.client.httpClient;

import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.ClientConfiguration;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.RestServiceHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Base64;

public class HttpRequestCreator {

    public static HttpRequest create(RestService restService, ClientConfiguration clientConfiguration) {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

        addUriToRequest(requestBuilder, restService, clientConfiguration);
        addHttpMethodToRequest(requestBuilder, restService);
        addContentTypeHeader(requestBuilder, restService);
        setProxyServerCredentials(requestBuilder, clientConfiguration);

        return requestBuilder.build();
    }

    public static void addUriToRequest(HttpRequest.Builder httpRequestBuilder, RestService restService, ClientConfiguration clientConfiguration) {

        String uri = RestServiceHelper.getServiceUrl(restService, clientConfiguration);

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

    private static void setProxyServerCredentials(HttpRequest.Builder httpRequestBuilder, ClientConfiguration clientConfiguration) {

        if (!clientConfiguration.hasProxyServerCredentials()) return;

        String loginString = clientConfiguration.getProxyServerUser() + ":" + clientConfiguration.getProxyServerPassword();
        String encoded = new String(Base64.getEncoder().encode(loginString.getBytes()));
        httpRequestBuilder.setHeader("Proxy-Authorization", "Basic " + encoded);

    }

}
