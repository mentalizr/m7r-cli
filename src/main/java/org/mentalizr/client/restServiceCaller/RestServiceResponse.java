package org.mentalizr.client.restServiceCaller;

import de.arthurpicht.utils.core.strings.Strings;

import java.net.http.HttpResponse;

public class RestServiceResponse {

    private RestServiceStatus restServiceStatus;
    private String responseBody;

    public RestServiceResponse(HttpResponse httpResponse) {
        this.restServiceStatus = RestServiceStatus.getStatus(httpResponse);
        this.responseBody = (String) httpResponse.body();
    }

    public RestServiceStatus getRestServiceStatus() {
        return restServiceStatus;
    }

    public boolean hasResponseBody() {
        return Strings.isNotNullAndNotEmpty(this.responseBody);
    }

    public String getResponseBody() {
        return responseBody;
    }
}
