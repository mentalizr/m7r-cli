package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.client.restService.Logout;
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
        RestService restService = new Logout();

        String body = RestServiceCaller.call(restService, this.cliConfiguration);
        System.out.println("[OK] Successfully logged out from " + this.cliConfiguration.getServer());

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("HttpBody: " + body);
        }
    }

}
