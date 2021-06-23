package org.mentalizr.cli.backup;

import de.arthurpicht.utils.io.compress.Zip;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.cli.commands.backup.BackupSpecificOptions;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BackupFS {

    private final BackupSpecificOptions backupSpecificOptions;
    private final BackupFileLocation backupFileLocation;

    public BackupFS(BackupSpecificOptions backupSpecificOptions) {
        this.backupSpecificOptions = backupSpecificOptions;
        this.backupFileLocation = new BackupFileLocation(this.backupSpecificOptions);
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

        Path patientBackupDir = backupFileLocation.getBackupDirPatient(patientRestoreSO);
        String filename = patientRestoreSO.getUuid() + ".patientRestoreSO.json";
        Path patientBackupFile = patientBackupDir.resolve(filename);

        try {
            Files.writeString(patientBackupFile, patientRestoreSOJson, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new CliException("Could not write to file [" + patientBackupFile.toAbsolutePath() + "]. " + e.getMessage(), e);
        }
    }

    public void backup(AccessKeyRestoreSO accessKeyRestoreSO) {
        String accessKeyRestoreJson = AccessKeyRestoreSOX.toJsonWithFormatting(accessKeyRestoreSO) + "\n";

        Path backupDir = backupFileLocation.getBackupDirAccessKey(accessKeyRestoreSO);
        String filename = accessKeyRestoreSO.getId() + ".accessKeyRestoreSO.json";
        Path backupFile = backupDir.resolve(filename);

        try {
            Files.writeString(backupFile, accessKeyRestoreJson, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new CliException("Could not write to file [" + backupFile.toAbsolutePath() + "]. " + e.getMessage(), e);
        }
    }

    public void createArchive() throws IOException {
        Path destinationDir;
        if (this.backupSpecificOptions.hasDirectory()) {
            destinationDir = this.backupSpecificOptions.getDirectory();
        } else {
            destinationDir = Paths.get(".").normalize().toAbsolutePath();
        }
        Path destination = destinationDir.resolve(this.backupFileLocation.getTimeStampedBackupDirName() + ".zip");
        Zip.zip(this.backupFileLocation.getBackupDir(), destination, true);
        FileUtils.rmDir(this.backupFileLocation.getBackupDir());
    }

}
