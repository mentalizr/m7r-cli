package org.mentalizr.cli.backup;

import org.mentalizr.cli.exceptions.CliException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class RecoverFileLocation {

    private Path backupDir;

    public RecoverFileLocation(Path backupDir) {

        if (!Files.exists(backupDir))
            throw new CliException("Backup directory to recover from not found [" + backupDir.toAbsolutePath() + "]");

        this.backupDir = backupDir;
    }

    public List<Path> getAllTherapistFiles() {

        Path therapistDir = this.backupDir.resolve("therapist");

        try {
            return Files
                    .walk(therapistDir)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CliException("Could not read therapist restore files from directory [" + therapistDir.toAbsolutePath() + "] " + e.getMessage(), e);
        }
    }



}
