package org.mentalizr.cli.backup;

import org.mentalizr.cli.exceptions.CliException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class RecoverFileLocation {

    private final Path backupDir;

    public RecoverFileLocation(Path backupDir) {

        if (!Files.exists(backupDir))
            throw new CliException("Backup directory to recover from not found [" + backupDir.toAbsolutePath() + "]");

        this.backupDir = backupDir;
    }

    public List<Path> getAllTherapistFiles() {
        Path dir = this.backupDir.resolve("therapist");
        return getAllContainingRegularFiles(dir);
    }

    public List<Path> getAllProgramFiles() {
        Path dir = this.backupDir.resolve("program");
        return getAllContainingRegularFiles(dir);
    }

    private List<Path> getAllContainingRegularFiles(Path dir) {
        try {
            return Files
                    .walk(dir)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CliException("Could not read restore directory [" + dir.toAbsolutePath() + "] " + e.getMessage(), e);
        }
    }

}
