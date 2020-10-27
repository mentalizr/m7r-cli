package org.mentalizr.cli.httpClient;

import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.exceptions.CliException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Base64;

public class HttpRequestCreator {

    public static HttpRequest create(CliConfiguration cliConfiguration) {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

        String uri = cliConfiguration.getServer();
        try {
            requestBuilder.uri(new URI(uri));
        } catch (URISyntaxException e) {
            throw new CliException(e);
        }

        requestBuilder.GET();

        if (cliConfiguration.hasProxyServerCredentials()) {
            setProxyServerCredentials(requestBuilder, cliConfiguration);
        }

        return requestBuilder.build();

    }

    private static void setProxyServerCredentials(HttpRequest.Builder httpRequestBuilder, CliConfiguration cliConfiguration) {

        String loginString = cliConfiguration.getProxyServerUser() + ":" + cliConfiguration.getProxyServerPassword();
        String encoded = new String(Base64.getEncoder().encode(loginString.getBytes()));
        httpRequestBuilder.setHeader("Proxy-Authorization", "Basic " + encoded);

    }
}
