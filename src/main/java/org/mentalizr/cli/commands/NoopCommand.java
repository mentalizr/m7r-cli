package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.NoopService;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class NoopCommand extends CommandExecutor {

    public NoopCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {
        RestService restService = new NoopService();
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        String body = RestServiceCaller.call(restCallContext, restService);

        System.out.println("[OK] noop");

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }

    }

}
