package org.mentalizr.cli.backup;

import org.mentalizr.cli.config.CliConfigurationFiles;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupFileLocation {

    private final Path backupDir;

    public BackupFileLocation() {

        Path backupRootDir = CliConfigurationFiles.getBackupRootDir().toPath();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        this.backupDir = backupRootDir.resolve(timestamp);
        try {
            Files.createDirectories(this.backupDir);
        } catch (IOException e) {
            throw new CliException("Could not create backup directory [" + this.backupDir.toAbsolutePath() + "] " + e.getMessage(), e);
        }
    }

    public Path getBackupDir() {
        return this.backupDir;
    }

    public Path getBackupDirTherapist(TherapistRestoreSO therapistRestoreSO) {
        Path therapistBackupDir = this.backupDir.resolve("therapist").resolve(therapistRestoreSO.getUuid());
        createBackupDir(therapistBackupDir);
        return therapistBackupDir;
    }

    public Path getBackupDirPatient(PatientRestoreSO patientRestoreSO) {
        Path patientBackupDir = this.backupDir.resolve("patient").resolve(patientRestoreSO.getUuid());
        createBackupDir(patientBackupDir);
        return patientBackupDir;
    }

    public Path getBackupDirProgram() {
        Path programBackupDir = this.backupDir.resolve("program");
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
