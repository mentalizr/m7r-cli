package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.TherapistShowService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSOX;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class BackupTherapists {

    public static void exec(BackupFileLocation backupFileLocation, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        System.out.println("Creating backup to [" + backupFileLocation.getBackupDir().toAbsolutePath() + "].");

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        RestService restService = new TherapistShowService();
        String body = RestServiceCaller.call(restCallContext, restService);

        TherapistRestoreCollectionSO therapistRestoreCollectionSO = TherapistRestoreCollectionSOX.fromJson(body);
        List<TherapistRestoreSO> collection = therapistRestoreCollectionSO.getCollection();

        for (TherapistRestoreSO therapistRestoreSO : collection) {
            String therapistRestoreSOJson = TherapistRestoreSOX.toJsonWithFormatting(therapistRestoreSO) + "\n";

            Path therapistBackupDir = backupFileLocation.getBackupDirTherapist(therapistRestoreSO);
            String filename = therapistRestoreSO.getUuid() + ".therapistRestoreSO.json";
            Path therapistBackupFile = therapistBackupDir.resolve(filename);

            try {
                Files.writeString(therapistBackupFile, therapistRestoreSOJson, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                throw new CliException("Could not write to file [" + therapistBackupFile.toAbsolutePath() + "]. " + e.getMessage(), e);
            }

        }

        System.out.println("[OK] " + collection.size() + " therapists backuped.");
    }

}
