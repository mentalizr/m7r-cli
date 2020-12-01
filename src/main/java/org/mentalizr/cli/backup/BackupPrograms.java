package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.ProgramGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.util.List;

public class BackupPrograms {

    public static void exec(BackupFS backupFS, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        System.out.println("Backup programs ...");

        ProgramCollectionSO programCollectionSO = ProgramGetAllService.call(cliContext);

        writeProgramsToFile(programCollectionSO, backupFS);

        System.out.println("[OK] " + programCollectionSO.getCollection().size() + " programs backuped.");
    }

    private static void writeProgramsToFile(ProgramCollectionSO programCollectionSO, BackupFS backupFS) {
        List<ProgramSO> collection = programCollectionSO.getCollection();
        for (ProgramSO programSO : collection) {
            backupFS.backup(programSO);
        }
    }

}
