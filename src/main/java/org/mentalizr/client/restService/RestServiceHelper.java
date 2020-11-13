package org.mentalizr.client.restService;

import org.mentalizr.cli.Const;
import org.mentalizr.client.RESTCallContext;

public class RestServiceHelper {

    public static String getServiceUrl(RestService restService, RESTCallContext RESTCallContext) {
        String server = RESTCallContext.getClientConfiguration().getServer();
        String url = server + Const.CONTEXT_PATH + restService.getServiceName();
        return url;
    }

}
