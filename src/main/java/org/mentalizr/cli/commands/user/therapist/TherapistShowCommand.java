package org.mentalizr.cli.commands.user.therapist;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.TherapistGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.util.List;

public class TherapistShowCommand extends CommandExecutor {

    public TherapistShowCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        TherapistRestoreCollectionSO therapistRestoreCollectionSO = TherapistGetAllService.call(this.cliContext);
        List<TherapistRestoreSO> collection = therapistRestoreCollectionSO.getCollection();

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

}
