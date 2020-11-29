package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientShowService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.util.List;

public class BackupPatients {

    public static void exec(BackupFS backupFS, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        System.out.println("Backup patients ...");

        PatientRestoreCollectionSO patientRestoreCollectionSO = callPatientShowService(cliContext);

        writeEntitiesToFile(patientRestoreCollectionSO, backupFS);

        System.out.println("[OK] " + patientRestoreCollectionSO.getCollection().size() + " patients backuped.");
    }

    private static PatientRestoreCollectionSO callPatientShowService(CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        PatientShowService service = new PatientShowService(restCallContext);
        return service.call();
    }

    private static void writeEntitiesToFile(PatientRestoreCollectionSO patientRestoreCollectionSO, BackupFS backupFS) {
        List<PatientRestoreSO> collection = patientRestoreCollectionSO.getCollection();
        for (PatientRestoreSO patientRestoreSO : collection) {
            backupFS.backup(patientRestoreSO);
        }
    }

}
