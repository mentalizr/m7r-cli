package org.mentalizr.cli.commands.program;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.ProgramDeleteService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.util.List;

public class ProgramDeleteCommand implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);
        CommandExecutorHelper.checkedInit(cliContext);

        if (!optionParserResultSpecific.hasOption(M7rCli.OPTION__PROGRAM))
            throw new CliException("Please specify --program option.");
        String programId = optionParserResultSpecific.getValue(M7rCli.OPTION__PROGRAM).trim();

        callService(programId, cliContext);

        System.out.println("[OK] Program [" + programId + "] deleted.");
    }

    private void callService(String programId, CliContext cliContext) throws CommandExecutorException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            new ProgramDeleteService(programId, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
