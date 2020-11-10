package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.client.restService.Noop;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;

public class NoopCommand extends CommandExecutor {

    public NoopCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() {
        RestService restService = new Noop();

        String body = this.checkedCall(restService);

        System.out.println("[OK] noop");

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }

    }

}
