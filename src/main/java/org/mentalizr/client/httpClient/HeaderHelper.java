package org.mentalizr.client.httpClient;

import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.config.CliConfiguration;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HeaderHelper {

    public static void showRequestHeaders(HttpRequest httpRequest) {
        HttpHeaders httpHeadersRequest = httpRequest.headers();
        System.out.println("Request-Headers:");
        HeaderHelper.showHeaders(httpHeadersRequest);
    }

    public static void showReponseHeaders(HttpResponse httpResponse) {
        HttpHeaders httpHeadersResponse = httpResponse.headers();
        System.out.println("Response-Headers:");
        HeaderHelper.showHeaders(httpHeadersResponse);
    }

    public static void showHeaders(HttpHeaders httpHeaders) {

        Map<String, List<String>> headerMap = httpHeaders.map();
        Set<String> headerNameSet = headerMap.keySet();

        if (headerNameSet.isEmpty()) {
            System.out.println("    <none>");
            return;
        }

        for (String headerName : headerNameSet) {
            List<String> headerValueList = httpHeaders.allValues(headerName);
            String values = Strings.listing(headerValueList, ", ");
            String outputString = headerName + " = " + values;
            System.out.println("    " + outputString);
        }

    }

}
