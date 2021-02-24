package org.mentalizr.cli.commands.user.patient;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.restService.userAdmin.PatientGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;

import java.util.List;

public class PatientShowCommand implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        List<PatientRestoreSO> collection = callService(cliContext);

        // TODO debug out of restoreSO, see AccessKeyShowCommand

        if (collection.size() == 0) {
            System.out.println("No patients found.");
        } else {
            System.out.println(collection.size() + " patient" + (collection.size() > 1 ? "s" : "") + " found:");
            System.out.println("ID                                   | username");
            System.out.println("-------------------------------------+---------------------");
            for (PatientRestoreSO patientRestoreSO : collection) {
                System.out.println(patientRestoreSO.getUuid() + " | " + patientRestoreSO.getUsername());
            }
        }
    }

    private List<PatientRestoreSO> callService(CliContext cliContext) throws CommandExecutorException {

        try {
            return PatientGetAllService.call(cliContext).getCollection();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
