package org.mentalizr.cli.commands.user.therapist;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.TherapistShowService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSOX;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.util.List;

public class TherapistShowCommand extends CommandExecutor {

    public TherapistShowCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        TherapistShowService restService = new TherapistShowService(restCallContext);
        TherapistRestoreCollectionSO therapistRestoreCollectionSO = restService.call();

        List<TherapistRestoreSO> collection = therapistRestoreCollectionSO.getCollection();

        System.out.println("[OK] Show all therapists. Found " + collection.size() + ".");

        for (TherapistRestoreSO therapistRestoreSO : collection) {
            System.out.println(therapistRestoreSO.getUuid() + " | " + therapistRestoreSO.getUsername());
        }

    }

}
