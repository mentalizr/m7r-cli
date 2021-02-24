package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.TherapistGetService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

public class TherapistGetCommand implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        OptionParserResult optionParserResultSpecific = cliCall.getOptionParserResultSpecific();
        if (optionParserResultSpecific.hasOption(M7rCli.ID_USER)) {

            String username = optionParserResultSpecific.getValue(M7rCli.ID_USER);

            TherapistRestoreSO therapistRestoreSO = callService(username, cliContext);

            System.out.println("[OK] Get therapist '" + therapistRestoreSO.getUsername() + "':");
            System.out.println(TherapistRestoreSOX.toJsonWithFormatting(therapistRestoreSO));

        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_UUID)) {
            throw new RuntimeException("Not implemented yet.");
        } else {
            System.out.println("[ERROR] Please specify --user or --uuid option.");
        }
    }

    private TherapistRestoreSO callService(String username, CliContext cliContext) throws CommandExecutorException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            return new TherapistGetService(username, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }
}
