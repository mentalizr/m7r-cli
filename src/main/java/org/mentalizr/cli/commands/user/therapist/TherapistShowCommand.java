package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.restService.userAdmin.TherapistGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.util.List;

public class TherapistShowCommand implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        List<TherapistRestoreSO> collection = callService(cliContext);

        if (collection.isEmpty()) {
            System.out.println("No therapists found.");
        } else {
            System.out.println(collection.size() + " therapist" + (collection.size() > 1 ? "s" : "") + " found:");
            System.out.println("ID                                   | username");
            System.out.println("-------------------------------------+---------------------");

            for (TherapistRestoreSO therapistRestoreSO : collection) {
                System.out.println(therapistRestoreSO.getUuid() + " | " + therapistRestoreSO.getUsername());
            }
        }
    }

    private List<TherapistRestoreSO> callService(CliContext cliContext) throws CommandExecutorException {
        try {
            TherapistRestoreCollectionSO therapistRestoreCollectionSO = TherapistGetAllService.call(cliContext);
            return therapistRestoreCollectionSO.getCollection();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
