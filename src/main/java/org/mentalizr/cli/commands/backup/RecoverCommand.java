package org.mentalizr.cli.commands.backup;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.backup.*;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.restService.accessKey.AccessKeyGetAllService;
import org.mentalizr.client.restService.userAdmin.PatientGetAllService;
import org.mentalizr.client.restService.userAdmin.ProgramGetAllService;
import org.mentalizr.client.restService.userAdmin.TherapistGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.ProgramSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.io.IOException;
import java.util.List;

public class RecoverCommand implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        RecoverSpecificOptions recoverSpecificOptions
                = new RecoverSpecificOptions(cliCall.getOptionParserResultSpecific());

        RecoverFileLocation recoverFileLocation;
        try {
            recoverFileLocation = new RecoverFileLocation(recoverSpecificOptions);
        } catch (IOException e) {
            throw new CommandExecutorException("IO-Error: " + e.getMessage(), e);
        }

        assertDBisEmpty(cliContext);

        executeRecover(recoverFileLocation, cliContext);

        recoverFileLocation.clean();

        System.out.println("[OK] Recovered from backup.");
    }

    private void assertDBisEmpty(CliContext cliContext) throws CommandExecutorException {

        try {
            List<ProgramSO> collectionProgram = ProgramGetAllService.call(cliContext).getCollection();
            if (!collectionProgram.isEmpty())
                throw new CliException("Cannot recover user database due to preexisting programs.");

            List<TherapistRestoreSO> collectionTherapist = TherapistGetAllService.call(cliContext).getCollection();
            if (!collectionTherapist.isEmpty())
                throw new CliException("Cannot recover user database due to preexisting therapists.");

            List<PatientRestoreSO> collectionPatient = PatientGetAllService.call(cliContext).getCollection();
            if (!collectionPatient.isEmpty())
                throw new CliException("Cannot recover user database due to preexisting patients.");

            List<AccessKeyRestoreSO> collectionAccessKey = AccessKeyGetAllService.call(cliContext).getCollection();
            if (!collectionAccessKey.isEmpty()) {
                throw new CliException("Cannot recover user database due to preexisting access keys.");
            }
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

    private void executeRecover(RecoverFileLocation recoverFileLocation, CliContext cliContext) throws CommandExecutorException {
        try {
            RecoverPrograms.exec(recoverFileLocation, cliContext);
            RecoverTherapists.exec(recoverFileLocation, cliContext);
            RecoverPatients.exec(recoverFileLocation, cliContext);
            RecoverAccessKeys.exec(recoverFileLocation, cliContext);
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
