package org.mentalizr.cli.backup;

import de.arthurpicht.utils.core.strings.Strings;
import de.arthurpicht.utils.io.compress.Zip;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.cli.commands.backup.RecoverSpecificOptions;
import org.mentalizr.cli.config.CliConfigurationFiles;
import org.mentalizr.cli.exceptions.CliException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class RecoverFileLocation {

    private final boolean isArchive;
    private final Path backupDir;

    public RecoverFileLocation(RecoverSpecificOptions recoverSpecificOptions) throws IOException {
        if (recoverSpecificOptions.hasDirectory()) {
            this.isArchive = false;
            this.backupDir = recoverSpecificOptions.getDirectory();
        } else {
            this.isArchive = true;

            Path tempDir = CliConfigurationFiles.getTempDir().toPath();
            Files.createDirectories(tempDir);

            Path backupArchive = recoverSpecificOptions.getArchive();
            String archiveFileName = backupArchive.getFileName().toString();
            this.backupDir = tempDir.resolve(cutOffZipPostfix(archiveFileName));

            Zip.unzip(backupArchive, tempDir);
        }
    }

    private String cutOffZipPostfix(String filename) {
        if (filename.endsWith(".zip")) {
            return Strings.cutEnd(filename, 4);
        } else {
            return filename;
        }
    }

    public List<Path> getAllTherapistFiles() {
        Path dir = this.backupDir.resolve("therapist");
        return getAllContainingRegularFiles(dir);
    }

    public List<Path> getAllPatientFiles() {
        Path dir = this.backupDir.resolve("patient");
        return getAllContainingRegularFiles(dir);
    }

    public List<Path> getAllAccessKeyFiles() {
        Path dir = this.backupDir.resolve("accessKey");
        return getAllContainingRegularFiles(dir);
    }

    public List<Path> getAllProgramFiles() {
        Path dir = this.backupDir.resolve("program");
        return getAllContainingRegularFiles(dir);
    }

    public void clean() {
        if (!this.isArchive) return;

        try {
            FileUtils.rmDir(this.backupDir);
        } catch (IOException e) {
            throw new CliException("Exception when cleaning temporary backup directory for unzipped archive: [" + this.backupDir + "].", e);
        }
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
