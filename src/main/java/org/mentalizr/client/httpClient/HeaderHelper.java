package org.mentalizr.client.httpClient;

import de.arthurpicht.utils.core.strings.Strings;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HeaderHelper {

    public static void showHeaders(HttpHeaders httpHeaders) {

        Map<String, List<String>> headerMap = httpHeaders.map();
        Set<String> headerNameSet = headerMap.keySet();
//        System.out.println("HeaderNameSet count: " + headerNameSet.size());
        for (String headerName : headerNameSet) {
            List<String> headerValueList = httpHeaders.allValues(headerName);
            String values = Strings.listing(headerValueList, ", ");
            String outputString = headerName + " = " + values;
            System.out.println("    " + outputString);
        }

    }

}
