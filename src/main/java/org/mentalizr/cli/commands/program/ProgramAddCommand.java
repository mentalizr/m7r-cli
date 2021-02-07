package org.mentalizr.cli.commands.program;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.ProgramAddService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.ProgramSO;

import java.util.List;

public class ProgramAddCommand implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);
        CommandExecutorHelper.checkedInit(cliContext);

        ProgramSO programSO = new ProgramSO();

        String programId = ConsoleReader.promptForMandatoryString("programId: ");
        programSO.setProgramId(programId);

        callService(programSO, cliContext);

        System.out.println("[OK] Program [" + programId + "] added.");
    }

    private void callService(ProgramSO programSO, CliContext cliContext) throws CommandExecutorException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            new ProgramAddService(programSO, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }
}
