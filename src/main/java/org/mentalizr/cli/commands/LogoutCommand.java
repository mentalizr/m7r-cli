package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.client.restService.Logout;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;

public class LogoutCommand extends CommandExecutor {

    public LogoutCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() {
        RestService restService = new Logout();

        String body = this.checkedCall(restService);
        System.out.println("[OK] Successfully logged out from " + this.cliConfiguration.getServer());

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("HttpBody: " + body);
        }
    }

}
