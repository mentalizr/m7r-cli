package org.mentalizr.cli.commands.sessionManagement;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.sessionManagement.LogoutService;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class LogoutCommand extends CommandExecutor {

    public LogoutCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        RestService restService = new LogoutService();
        String body = RestServiceCaller.call(restCallContext, restService);
        System.out.println("[OK] Successfully logged out from " + this.cliContext.getCliConfiguration().getServer());

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("HttpBody: " + body);
        }
    }

}
