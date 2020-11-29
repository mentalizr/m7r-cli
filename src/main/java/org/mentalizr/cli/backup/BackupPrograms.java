package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.ProgramShowService;
import org.mentalizr.client.restService.userAdmin.TherapistShowService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class BackupPrograms {

    public static void exec(BackupFS backupFS, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        System.out.println("Backup programs ...");

        ProgramCollectionSO programCollectionSO = callProgramShowService(cliContext);

        writeProgramsToFile(programCollectionSO, backupFS);

        System.out.println("[OK] " + programCollectionSO.getCollection().size() + " programs backuped.");
    }

    private static ProgramCollectionSO callProgramShowService(CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        ProgramShowService restService = new ProgramShowService(restCallContext);
        return restService.call();
    }

    private static void writeProgramsToFile(ProgramCollectionSO programCollectionSO, BackupFS backupFS) {
        List<ProgramSO> collection = programCollectionSO.getCollection();
        for (ProgramSO programSO : collection) {
            backupFS.backup(programSO);
        }
    }

}
