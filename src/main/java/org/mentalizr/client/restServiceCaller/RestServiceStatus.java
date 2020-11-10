package org.mentalizr.client.restServiceCaller;

import java.net.http.HttpResponse;

public enum RestServiceStatus {

    OK,
    AUTHENTICATION_FAILED,
    OTHER;

    private int responseCode;

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public static RestServiceStatus getStatus(HttpResponse httpResponse) {
        RestServiceStatus status;
        switch (httpResponse.statusCode()) {
            case 200:
                status = OK;
                status.setResponseCode(httpResponse.statusCode());
                return status;
            case 401:
                status = AUTHENTICATION_FAILED;
                status.setResponseCode(httpResponse.statusCode());
                return status;
            default:
                status = OTHER;
                status.setResponseCode(httpResponse.statusCode());
                return status;
        }
    }


}
