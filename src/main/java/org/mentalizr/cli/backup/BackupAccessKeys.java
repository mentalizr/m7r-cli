package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.accessKey.AccessKeyGetAllService;
import org.mentalizr.client.restService.userAdmin.PatientShowService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCollectionSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;

import java.util.List;

public class BackupAccessKeys {

    public static void exec(BackupFS backupFS, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        System.out.println("Backup access keys ...");

        AccessKeyCollectionSO accessKeyCollectionSO = callAccessKeyGetAllService(cliContext);

        writeEntitiesToFile(accessKeyCollectionSO, backupFS);

        System.out.println("[OK] " + accessKeyCollectionSO.getCollection().size() + " access keys backuped.");
    }

    private static AccessKeyCollectionSO callAccessKeyGetAllService(CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        AccessKeyGetAllService service = new AccessKeyGetAllService(restCallContext);
        return service.call();
    }

    private static void writeEntitiesToFile(AccessKeyCollectionSO accessKeyCollectionSO, BackupFS backupFS) {
        List<AccessKeyRestoreSO> collection = accessKeyCollectionSO.getCollection();
        for (AccessKeyRestoreSO accessKeyRestoreSO : collection) {
            backupFS.backup(accessKeyRestoreSO);
        }
    }

}
