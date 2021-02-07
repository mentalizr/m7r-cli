package org.mentalizr.cli.commands.program;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.restService.userAdmin.ProgramGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.util.List;

public class ProgramShowCommand implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);
        CommandExecutorHelper.checkedInit(cliContext);

        List<ProgramSO> collection = callService(cliContext);

        if (collection.size() == 0) {
            System.out.println("No programs found.");
        } else {
            System.out.println(collection.size() + " program" + (collection.size() > 1 ? "s" : "") + " found:");
            System.out.println("program id                           |");
            System.out.println("-------------------------------------+");
            for (ProgramSO programSO : collection) {
                System.out.println(programSO.getProgramId());
            }
        }
    }

    private List<ProgramSO> callService(CliContext cliContext) throws CommandExecutorException {
        try {
            return ProgramGetAllService.call(cliContext).getCollection();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
