package org.mentalizr.cli.commands.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.backup.BackupFileLocation;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.config.CliConfigurationFiles;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.ShowTherapistService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSOX;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;
import org.mentalizr.util.stringFilePersister.StringFilePersister;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BackupCommand extends CommandExecutor {

    public BackupCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        BackupFileLocation backupFileLocation;
        try {
            backupFileLocation = new BackupFileLocation();
        } catch (IOException e) {
            throw new CliException("Could not create or access backup directory: ["
                    + CliConfigurationFiles.getBackupRootDir().getAbsolutePath() + "] "
                    + e.getMessage(), e);
        }

        System.out.println("Creating backup to: " + backupFileLocation.getBackupDirTherapist().getAbsolutePath());

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        RestService restService = new ShowTherapistService();
        String body = RestServiceCaller.call(restCallContext, restService);

        TherapistRestoreCollectionSO therapistRestoreCollectionSO = TherapistRestoreCollectionSOX.fromJson(body);
        List<TherapistRestoreSO> collection = therapistRestoreCollectionSO.getCollection();

        for (TherapistRestoreSO therapistRestoreSO : collection) {
            String therapistRestoreSOJson = TherapistRestoreSOX.toJsonWithFormatting(therapistRestoreSO) + "\n";

            String filename = therapistRestoreSO.getUuid() + ".json";
            File therapistJsonFile = new File(backupFileLocation.getBackupDirTherapist(), filename);

            StringFilePersister stringFilePersister = new StringFilePersister(therapistJsonFile.toPath());
            try {
                stringFilePersister.write(therapistRestoreSOJson);
            } catch (IOException e) {
                throw new CliException("Could not write to file: [" + therapistJsonFile.getAbsolutePath() + "]. "
                        + e.getMessage(), e);
            }
        }

        System.out.println("[OK] " + collection.size() + " therapists backuped.");
    }
}
