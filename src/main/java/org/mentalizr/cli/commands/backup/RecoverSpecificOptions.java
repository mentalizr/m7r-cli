package org.mentalizr.cli.commands.backup;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.exceptions.CliException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RecoverSpecificOptions {

    private final Path directory;
    private final Path archive;

    public RecoverSpecificOptions(OptionParserResult optionParserResultSpecific) {

        if (hasNoneOfDirectoryOrArchive(optionParserResultSpecific))
            throw new CliException("None of --directory or --archive is specified.");

        if (optionParserResultSpecific.hasOption(M7rCli.OPTION__DIRECTORY)) {
            this.directory = Paths.get(optionParserResultSpecific.getValue(M7rCli.OPTION__DIRECTORY));
            this.archive = null;
            if (!Files.exists(this.directory) || !Files.isDirectory(this.directory))
                throw new CliException("Specified directory [" + this.directory + "] not found.");
        } else {
            this.directory = null;
            this.archive = Paths.get(optionParserResultSpecific.getValue(M7rCli.OPTION__ARCHIVE));
            if (!Files.exists(this.archive) || !Files.isRegularFile(this.archive))
                throw new CliException("Specified archive [" + this.archive + "] not found.");
        }
    }

    private boolean hasNoneOfDirectoryOrArchive(OptionParserResult optionParserResultSpecific) {
        return !optionParserResultSpecific.hasOption(M7rCli.OPTION__ARCHIVE)
                && !optionParserResultSpecific.hasOption(M7rCli.OPTION__DIRECTORY);
    }

    public boolean hasDirectory() {
        return this.directory != null;
    }

    public Path getDirectory() {
        if (!hasDirectory()) throw new IllegalStateException("No directory available. Check before calling.");
        return this.directory;
    }

    public boolean hasArchive() {
        return this.archive != null;
    }

    public Path getArchive() {
        if (!hasArchive()) throw new IllegalStateException("No archive available. Check before calling.");
        return this.archive;
    }

}
