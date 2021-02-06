package org.mentalizr.cli.commands.sessionManagement;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.sessionManagement.SessionStatusService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.SessionStatusSO;

import java.util.List;

public class StatusCommandExecutor implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);

        CommandExecutorHelper.checkedInit(cliContext);

        SessionStatusSO sessionStatusSO = callStatus(cliContext);

        System.out.println("[OK] Server " + cliContext.getCliConfiguration().getServer() + " is up and running.");
        if (sessionStatusSO.isValid()) {
            System.out.println("Session is valid. Logged in as " + sessionStatusSO.getUserRole() + ".");
        } else {
            System.out.println("No valid session.");
        }

    }

    private SessionStatusSO callStatus(CliContext cliContext) throws CommandExecutorException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            return new SessionStatusService(restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
