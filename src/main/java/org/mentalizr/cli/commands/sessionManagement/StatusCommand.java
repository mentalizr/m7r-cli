package org.mentalizr.cli.commands.sessionManagement;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.sessionManagement.SessionStatusService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.SessionStatus;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class StatusCommand extends CommandExecutor {

    public StatusCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        RestService restService = new SessionStatusService();

        String body = RestServiceCaller.call(restCallContext, restService);

        Jsonb jsonb = JsonbBuilder.create();
        SessionStatus sessionStatus = jsonb.fromJson(body, SessionStatus.class);

        System.out.println("[OK] Server " + this.cliContext.getCliConfiguration().getServer() + " is up and running.");
        if (sessionStatus.isValid()) {
            System.out.println("Session is valid. Logged in as " + sessionStatus.getUserRole() + ".");
        } else {
            System.out.println("No valid session.");
        }
    }

}
