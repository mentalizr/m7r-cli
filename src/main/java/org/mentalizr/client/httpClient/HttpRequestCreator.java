package org.mentalizr.client.httpClient;

import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.RestServiceHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Base64;

public class HttpRequestCreator {

    public static HttpRequest create(RestService restService, RESTCallContext restCallContext) {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

        addUriToRequest(requestBuilder, restService, restCallContext);
        addHttpMethodToRequest(requestBuilder, restService, restCallContext);
        addContentTypeHeader(requestBuilder, restService);
        setProxyServerCredentials(requestBuilder, restCallContext);

        return requestBuilder.build();
    }

    public static void addUriToRequest(HttpRequest.Builder httpRequestBuilder, RestService restService, RESTCallContext restCallContext) {

        String uri = RestServiceHelper.getServiceUrl(restService, restCallContext);

        if (restCallContext.isDebug())
            System.out.println("Service URL: " + uri);

        try {
            httpRequestBuilder.uri(new URI(uri));
        } catch (URISyntaxException e) {
            throw new CliException(e);
        }
    }

    public static void addHttpMethodToRequest(HttpRequest.Builder httpRequestBuilder, RestService restService, RESTCallContext restCallContext) {

        HttpMethod httpMethod = restService.getMethod();

        if (httpMethod == HttpMethod.GET) {

            httpRequestBuilder.GET();

            if (restCallContext.isDebug()) System.out.println("HttpMethod: GET");

        } else if (httpMethod == HttpMethod.POST) {

            String body = restService.getBody();
            httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));

            if (restCallContext.isDebug()) {
                System.out.println("Method: POST");
                System.out.println("Body: " + body);
            }

        } else {
            throw new RuntimeException("Unknown HttpMethod: " + httpMethod.name());
        }
    }

    public static void addContentTypeHeader(HttpRequest.Builder httpRequestBuilder, RestService restService) {
        if (restService.hasContentType()) {
            httpRequestBuilder.header("Content-Type", restService.getContentType());
        }
    }

    private static void setProxyServerCredentials(HttpRequest.Builder httpRequestBuilder, RESTCallContext restCallContext) {
        if (!restCallContext.getClientConfiguration().hasProxyServerCredentials()) return;

        String proxyServerUser = restCallContext.getClientConfiguration().getProxyServerUser();
        String proxyServerPassword = restCallContext.getClientConfiguration().getProxyServerPassword();
        String loginString = proxyServerUser + ":" + proxyServerPassword;
        String encoded = new String(Base64.getEncoder().encode(loginString.getBytes()));
        httpRequestBuilder.setHeader("Proxy-Authorization", "Basic " + encoded);
    }

}
