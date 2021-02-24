package org.mentalizr.cli.commands.sessionManagement;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.sessionManagement.LogoutService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class LogoutCommandExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        String body = callLogout(cliContext);
        System.out.println("[OK] Successfully logged out from " + cliContext.getCliConfiguration().getServer());

        if (cliContext.isDebug()) {
            System.out.println("HttpBody: " + body);
        }
    }

    private String callLogout(CliContext cliContext) throws CommandExecutorException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            return new LogoutService(restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
