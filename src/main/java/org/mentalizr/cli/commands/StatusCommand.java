package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.client.ClientContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.SessionStatusService;
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
        RestService restService = new SessionStatusService();
        ClientContext clientContext = this.getClientContext();

        String body = RestServiceCaller.call(restService, clientContext);

        Jsonb jsonb = JsonbBuilder.create();
        SessionStatus sessionStatus = jsonb.fromJson(body, SessionStatus.class);

        System.out.println("[OK] Status");
        System.out.println("Server " + this.cliContext.getCliConfiguration().getServer() + " is up and running.");
        if (sessionStatus.isValid()) {
            System.out.println("Logged in as " + sessionStatus.getUserRole());
        } else {
            System.out.println("Session is logged out.");
        }
    }

}
