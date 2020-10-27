package org.mentalizr.cli.httpClient;

import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.exceptions.CliException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Properties;

public class HttpClientCreator {

    public static HttpClient create(CliConfiguration cliConfiguration) {

        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20));
//                .authenticator(Authenticator.getDefault())

        if (cliConfiguration.isTrustAll()) {
            setTrustAllCerts(httpClientBuilder);
        }

        if (cliConfiguration.hasProxyServer()) {
            setProxy(httpClientBuilder, cliConfiguration);
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

    private static void setProxy(HttpClient.Builder httpClientBuilder, CliConfiguration cliConfiguration) {

        String proxyServer = cliConfiguration.getProxyServer();
        int proxyPort = cliConfiguration.getProxyPort();

        httpClientBuilder.proxy(ProxySelector.of(new InetSocketAddress(proxyServer, proxyPort)));

        if (cliConfiguration.hasProxyServerCredentials()) {
            System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        }

    }
}
