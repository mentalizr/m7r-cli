package org.mentalizr.client.restService;

import org.mentalizr.cli.Const;
import org.mentalizr.client.ClientConfiguration;

public class RestServiceHelper {

    public static String getServiceUrl(RestService restService, ClientConfiguration clientConfiguration) {
        String url = clientConfiguration.getServer() + Const.CONTEXT_PATH + restService.getServiceName();
        return url;
    }

}
