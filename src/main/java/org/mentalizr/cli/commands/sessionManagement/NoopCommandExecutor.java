package org.mentalizr.cli.commands.sessionManagement;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.sessionManagement.NoopService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.util.List;

public class NoopCommandExecutor implements CommandExecutor {

    @Override
    public void execute(
            OptionParserResult optionParserResultGlobal,
            List<String> commandList,
            OptionParserResult optionParserResultSpecific,
            List<String> parameterList)
            throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);

        CommandExecutorHelper.checkedInit(cliContext);

        String body = callNoopService(cliContext);
        System.out.println("[OK] noop");

        if (cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }
    }

    private String callNoopService(CliContext cliContext) throws CommandExecutorException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            return new NoopService(restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
