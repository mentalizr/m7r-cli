package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.helper.ServiceObjectHelper;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.TherapistRestoreService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

public class TherapistRestoreCommand extends CommandExecutor {

    public TherapistRestoreCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        if (!optionParserResultSpecific.hasOption(M7rCli.ID_FROM_FILE)) {
            throw new CliException("Specify --from-file option.");
        }

        String fileName = optionParserResultSpecific.getValue(M7rCli.ID_FROM_FILE);
        TherapistRestoreSO therapistRestoreSO = ServiceObjectHelper.therapistRestoreSOFromFile(fileName);

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        new TherapistRestoreService(therapistRestoreSO, restCallContext).call();

        System.out.println("[OK] User [" + therapistRestoreSO.getUsername() + "] restored.");
    }

}
