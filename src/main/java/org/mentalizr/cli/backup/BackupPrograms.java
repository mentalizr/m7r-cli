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

    public static void exec(BackupFileLocation backupFileLocation, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        System.out.println("Creating backup to [" + backupFileLocation.getBackupDir().toAbsolutePath() + "].");

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        RestService restService = new ProgramShowService();
        String body = RestServiceCaller.call(restCallContext, restService);

        ProgramCollectionSO programCollectionSO = ProgramCollectionSOX.fromJson(body);
        List<ProgramSO> collection = programCollectionSO.getCollection();

        for (ProgramSO programSO : collection) {
            String programSOJson = ProgramSOX.toJson(programSO) + "\n";

            Path backupDir = backupFileLocation.getBackupDirProgram(programSO);
            String filename = programSO.getProgramId() + ".programSO.json";
            Path therapistBackupFile = backupDir.resolve(filename);

            try {
                Files.writeString(therapistBackupFile, programSOJson, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                throw new CliException("Could not write to file [" + therapistBackupFile.toAbsolutePath() + "]. " + e.getMessage(), e);
            }
        }

        System.out.println("[OK] " + collection.size() + " programs backuped.");
    }

}
