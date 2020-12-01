package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.TherapistGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.util.List;

public class BackupTherapists {

    public static void exec(BackupFS backupFS, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        System.out.println("Backup therapists ...");

        TherapistRestoreCollectionSO therapistRestoreCollectionSO = callTherapistShowService(cliContext);

        writeEntitiesToFile(therapistRestoreCollectionSO, backupFS);

        System.out.println("[OK] " + therapistRestoreCollectionSO.getCollection().size() + " therapists backuped.");
    }

    private static TherapistRestoreCollectionSO callTherapistShowService(CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        TherapistGetAllService service = new TherapistGetAllService(restCallContext);
        return service.call();
    }

    private static void writeEntitiesToFile(TherapistRestoreCollectionSO therapistRestoreCollectionSO, BackupFS backupFS) {
        List<TherapistRestoreSO> collection = therapistRestoreCollectionSO.getCollection();
        for (TherapistRestoreSO therapistRestoreSO : collection) {
            backupFS.backup(therapistRestoreSO);
        }
    }

}
