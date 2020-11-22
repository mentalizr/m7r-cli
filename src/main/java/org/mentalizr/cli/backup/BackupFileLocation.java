package org.mentalizr.cli.backup;

import org.mentalizr.cli.config.CliConfigurationFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupFileLocation {

    private File backupDir;

//    private File backupDirProgram;
    private File backupDirTherapist;
//    private File backupDirPatientLogin;
//    private File backupDirPatientAccessKey;

    public BackupFileLocation() throws IOException {

        File backupRootDir = CliConfigurationFiles.getBackupRootDir();
        if (!backupRootDir.exists()) Files.createDirectories(backupRootDir.toPath());

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        this.backupDir = new File(backupRootDir, timestamp);
        Files.createDirectory(this.backupDir.toPath());

        this.backupDirTherapist = new File(this.backupDir, "therapist");
        Files.createDirectory(this.backupDirTherapist.toPath());

//        this.backupDirProgram = new File(this.backupDir, "program");
//        Files.createDirectory(this.backupDirProgram.toPath());
    }

    public File getBackupDirTherapist() {
        return backupDirTherapist;
    }

}
