package org.mentalizr.cli.backup;

import org.mentalizr.cli.commands.backup.BackupSpecificOptions;
import org.mentalizr.cli.config.CliConfigurationFiles;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupFileLocation {

    private static final String SUB_DIR_THERAPIST = "therapist";
    private static final String SUB_DIR_PATIENT = "patient";
    private static final String SUB_DIR_ACCESS_KEY = "accessKey";
    private static final String SUB_DIR_PROGRAM = "program";

    private final String timestamp;
    private final Path backupDir;

    public BackupFileLocation(BackupSpecificOptions backupSpecificOptions) {

        Path backupRootDir;
        if (backupSpecificOptions.isArchive()) {
            backupRootDir = CliConfigurationFiles.getTempDir().toPath();
        } else if (backupSpecificOptions.hasDirectory()) {
            backupRootDir = backupSpecificOptions.getDirectory();
        } else {
            backupRootDir = CliConfigurationFiles.getDefaultBackupRootDir().toPath();
        }
        this.timestamp = createTimestamp();
        this.backupDir = backupRootDir.resolve(getTimeStampedBackupDirName());

        try {
            Files.createDirectories(this.backupDir);
            Files.createDirectories(this.backupDir.resolve(SUB_DIR_THERAPIST));
            Files.createDirectories(this.backupDir.resolve(SUB_DIR_PATIENT));
            Files.createDirectories(this.backupDir.resolve(SUB_DIR_ACCESS_KEY));
            Files.createDirectories(this.backupDir.resolve(SUB_DIR_PROGRAM));
        } catch (IOException e) {
            throw new CliException("Could not create backup directory [" + this.backupDir.toAbsolutePath() + "] " + e.getMessage(), e);
        }
    }

    public String getTimeStampedBackupDirName() {
        return this.timestamp + "-m7r-userdb-backup";
    }

    private String createTimestamp() {
        return new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
    }

    public Path getBackupDir() {
        return this.backupDir;
    }

    public Path getBackupDirTherapist(TherapistRestoreSO therapistRestoreSO) {
        Path therapistBackupDir = this.backupDir.resolve(SUB_DIR_THERAPIST).resolve(therapistRestoreSO.getUuid());
        createBackupDir(therapistBackupDir);
        return therapistBackupDir;
    }

    public Path getBackupDirPatient(PatientRestoreSO patientRestoreSO) {
        Path patientBackupDir = this.backupDir.resolve(SUB_DIR_PATIENT).resolve(patientRestoreSO.getUuid());
        createBackupDir(patientBackupDir);
        return patientBackupDir;
    }

    public Path getBackupDirAccessKey(AccessKeyRestoreSO accessKeyRestoreSO) {
        Path accessKeyBackupDir = this.backupDir.resolve(SUB_DIR_ACCESS_KEY).resolve(accessKeyRestoreSO.getId());
        createBackupDir(accessKeyBackupDir);
        return accessKeyBackupDir;
    }

    public Path getBackupDirProgram() {
        Path programBackupDir = this.backupDir.resolve(SUB_DIR_PROGRAM);
        createBackupDir(programBackupDir);
        return programBackupDir;
    }

    private void createBackupDir(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new CliException("Could not create backup directory [" + dir.toAbsolutePath() + "]. " + e.getMessage(), e);
        }
    }

}
