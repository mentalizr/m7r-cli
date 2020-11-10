package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.client.restService.Logout;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.RestServiceCaller;

public class LogoutCommand extends CommandExecutor {

    public LogoutCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() {
        RestService restService = new Logout();
        RestServiceCaller.call(restService, cliConfiguration);
    }

}
