package org.mentalizr.cli.backup;

import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class BackupFS {

    private final BackupFileLocation backupFileLocation;

    public BackupFS() {
        this.backupFileLocation = new BackupFileLocation();
    }

    public String getBackupDirAsString() {
        return this.backupFileLocation.getBackupDir().toAbsolutePath().toString();
    }

    public void backup(ProgramSO programSO) {
        String programSOJson = ProgramSOX.toJson(programSO) + "\n";

        Path backupDir = backupFileLocation.getBackupDirProgram();
        String filename = programSO.getProgramId() + ".programSO.json";
        Path therapistBackupFile = backupDir.resolve(filename);

        try {
            Files.writeString(therapistBackupFile, programSOJson, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new CliException("Could not write to file [" + therapistBackupFile.toAbsolutePath() + "]. " + e.getMessage(), e);
        }
    }

    public void backup(TherapistRestoreSO therapistRestoreSO) {
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

    public void backup(PatientRestoreSO patientRestoreSO) {
        String patientRestoreSOJson = PatientRestoreSOX.toJsonWithFormatting(patientRestoreSO) + "\n";

        Path therapistBackupDir = backupFileLocation.getBackupDirPatient(patientRestoreSO);
        String filename = patientRestoreSO.getUuid() + ".patientRestoreSO.json";
        Path patientBackupFile = therapistBackupDir.resolve(filename);

        try {
            Files.writeString(patientBackupFile, patientRestoreSOJson, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new CliException("Could not write to file [" + patientBackupFile.toAbsolutePath() + "]. " + e.getMessage(), e);
        }
    }

}
