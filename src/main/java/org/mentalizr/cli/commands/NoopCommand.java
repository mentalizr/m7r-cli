package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.client.restService.Noop;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.RestServiceCaller;

public class NoopCommand extends CommandExecutor {

    public NoopCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() {
        RestService restService = new Noop();
        RestServiceCaller.call(restService, cliConfiguration);
    }

}
