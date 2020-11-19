package org.mentalizr.client.httpClient;

import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.ClientConfiguration;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.cookieHandler.CookieStoreM7r;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.*;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Properties;

public class HttpClientCreator {

    public static HttpClient create(RESTCallContext restCallContext) {

        ClientConfiguration clientConfiguration = restCallContext.getClientConfiguration();

        CookieStore cookieStore = new CookieStoreM7r(restCallContext);
        CookieHandler.setDefault(new CookieManager(cookieStore, CookiePolicy.ACCEPT_ORIGINAL_SERVER));

        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .cookieHandler(CookieHandler.getDefault());
//                .authenticator(Authenticator.getDefault())

        if (clientConfiguration.isTrustAll()) {
            setTrustAllCerts(httpClientBuilder);
        }

        if (clientConfiguration.hasProxyServer()) {
            setProxy(httpClientBuilder, clientConfiguration);
        }

        return httpClientBuilder.build();
    }


    private static void setTrustAllCerts(HttpClient.Builder httpClientBuilder) {

        final Properties props = System.getProperties();
        props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new CliException(e);
        }

        httpClientBuilder.sslContext(sslContext);
    }

    private static void setProxy(HttpClient.Builder httpClientBuilder, ClientConfiguration clientConfiguration) {

        String proxyServer = clientConfiguration.getProxyServer();
        int proxyPort = clientConfiguration.getProxyPort();

        httpClientBuilder.proxy(ProxySelector.of(new InetSocketAddress(proxyServer, proxyPort)));

        if (clientConfiguration.hasProxyServerCredentials()) {
            System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        }

    }
}
