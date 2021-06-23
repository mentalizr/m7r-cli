package org.mentalizr.cli.commands.backup;

import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.M7rCli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupSpecificOptions {

    private final Path directory;
    private final boolean archive;

    public BackupSpecificOptions(OptionParserResult optionParserResultSpecific) throws CommandExecutorException {
        if (optionParserResultSpecific.hasOption(M7rCli.OPTION__DIRECTORY)) {
            this.directory = Paths.get(optionParserResultSpecific.getValue(M7rCli.OPTION__DIRECTORY));
            if (!Files.exists(this.directory))
                throw new CommandExecutorException("Specified destination directory not existing: [" + this.directory.toAbsolutePath() + "].");
        } else {
            this.directory = null;
        }
        this.archive = optionParserResultSpecific.hasOption(M7rCli.OPTION__ARCHIVE);
    }

    public boolean hasDirectory() {
        return this.directory != null;
    }

    public Path getDirectory() {
        if (this.directory == null) throw new IllegalStateException("Directory not specified. Check before calling.");
        return this.directory;
    }

    public boolean isArchive() {
        return this.archive;
    }

}
