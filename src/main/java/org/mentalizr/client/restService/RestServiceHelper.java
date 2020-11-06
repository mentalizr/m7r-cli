package org.mentalizr.client.restService;

import org.mentalizr.cli.Const;
import org.mentalizr.cli.config.CliConfiguration;

public class RestServiceHelper {

    public static String getServiceUrl(RestService restService, CliConfiguration cliConfiguration) {
        String url = cliConfiguration.getServer() + Const.CONTEXT_PATH + restService.getServiceName();
        return url;
    }

}
