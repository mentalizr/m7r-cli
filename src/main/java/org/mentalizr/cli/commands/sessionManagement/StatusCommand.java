package org.mentalizr.cli.commands.sessionManagement;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.sessionManagement.SessionStatusService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.SessionStatusSO;

public class StatusCommand extends CommandExecutor {

    public StatusCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        SessionStatusSO sessionStatusSO = new SessionStatusService(restCallContext).call();

        System.out.println("[OK] Server " + this.cliContext.getCliConfiguration().getServer() + " is up and running.");
        if (sessionStatusSO.isValid()) {
            System.out.println("Session is valid. Logged in as " + sessionStatusSO.getUserRole() + ".");
        } else {
            System.out.println("No valid session.");
        }
    }

}
