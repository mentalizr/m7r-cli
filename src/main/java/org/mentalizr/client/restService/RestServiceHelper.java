package org.mentalizr.client.restService;

import org.mentalizr.cli.Const;
import org.mentalizr.client.ClientConfiguration;
import org.mentalizr.client.ClientContext;

public class RestServiceHelper {

    public static String getServiceUrl(RestService restService, ClientContext clientContext) {
        String server = clientContext.getClientConfiguration().getServer();
        String url = server + Const.CONTEXT_PATH + restService.getServiceName();
        return url;
    }

}
