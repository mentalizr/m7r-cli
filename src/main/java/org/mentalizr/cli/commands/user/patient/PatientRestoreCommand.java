package org.mentalizr.cli.commands.user.patient;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.fileSystem.PatientRestoreSOFS;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientRestoreService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;

import java.nio.file.Paths;

public class PatientRestoreCommand implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        OptionParserResult optionParserResultSpecific = cliCall.getOptionParserResultSpecific();
        if (!optionParserResultSpecific.hasOption(M7rCli.ID_FROM_FILE)) {
            throw new CliException("Specify --from-file option.");
        }

        String fileName = optionParserResultSpecific.getValue(M7rCli.ID_FROM_FILE);
        PatientRestoreSO patientRestoreSO = PatientRestoreSOFS.fromFile(Paths.get(fileName));

        callService(patientRestoreSO, cliContext);

        System.out.println("[OK] Patient [" + patientRestoreSO.getUsername() + "] restored.");
    }

    private void callService(PatientRestoreSO patientRestoreSO, CliContext cliContext) throws CommandExecutorException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            new PatientRestoreService(patientRestoreSO, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
