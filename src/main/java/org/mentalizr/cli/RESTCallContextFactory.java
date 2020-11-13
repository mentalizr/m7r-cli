package org.mentalizr.cli;

import org.mentalizr.client.RESTCallContext;

public class RESTCallContextFactory {

    public static RESTCallContext getInstance(CliContext cliContext) {
        cliContext.assertCliConfiguration();
        boolean debug = cliContext.getCliCallGlobalConfiguration().isDebug();
        return new RESTCallContext(cliContext.getCliConfiguration(), debug);
    }

}
