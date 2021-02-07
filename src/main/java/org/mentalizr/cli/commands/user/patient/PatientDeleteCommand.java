package org.mentalizr.cli.commands.user.patient;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientDeleteService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.util.List;

public class PatientDeleteCommand implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);
        CommandExecutorHelper.checkedInit(cliContext);

        if (optionParserResultSpecific.hasOption(M7rCli.ID_USER)) {

            String username = optionParserResultSpecific.getValue(M7rCli.ID_USER).trim();

            callService(username, cliContext);

            System.out.println("[OK] Patient [" + username + "] deleted.");

        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_UUID)) {
            throw new RuntimeException("Not implemented yet.");
        } else {
            System.out.println("[ERROR] Please specify --user or --uuid option.");
        }
    }

    private void callService(String username, CliContext cliContext) throws CommandExecutorException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            new PatientDeleteService(username, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
