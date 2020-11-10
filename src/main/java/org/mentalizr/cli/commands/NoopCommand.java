package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.client.restService.Noop;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceCallerConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceCallerHttpException;

public class NoopCommand extends CommandExecutor {

    public NoopCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceCallerHttpException, RestServiceCallerConnectionException {
        RestService restService = new Noop();

        String body = RestServiceCaller.call(restService, this.cliConfiguration);

        System.out.println("[OK] noop");

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }

    }

}
