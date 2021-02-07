package org.mentalizr.cli.commands.user.accessKey;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.accessKey.AccessKeyDeleteService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyDeleteSO;

import java.util.List;

public class AccessKeyDeleteCommand implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);
        CommandExecutorHelper.checkedInit(cliContext);

        if (optionParserResultSpecific.hasOption(M7rCli.OPTION__ACCESS_KEY)) {

            String accessKey = optionParserResultSpecific.getValue(M7rCli.OPTION__ACCESS_KEY).trim();

            callService(accessKey, cliContext);

            System.out.println("[OK] Access key [" + accessKey + "] deleted.");

        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_UUID)) {

            // TODO
            throw new RuntimeException("Not implemented yet.");
        } else {
            System.out.println("[ERROR] Please specify --accessKey option.");
        }
    }

    private void callService(String accessKey, CliContext cliContext) throws CommandExecutorException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        AccessKeyDeleteSO accessKeyDeleteSO = new AccessKeyDeleteSO();
        accessKeyDeleteSO.setAccessKey(accessKey);
        try {
            new AccessKeyDeleteService(accessKeyDeleteSO, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
