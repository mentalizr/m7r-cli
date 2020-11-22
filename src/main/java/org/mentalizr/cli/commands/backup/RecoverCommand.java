package org.mentalizr.cli.commands.backup;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.backup.RecoverFileLocation;
import org.mentalizr.cli.backup.RecoverTherapist;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RecoverCommand extends CommandExecutor {

    public RecoverCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        if (!optionParserResultSpecific.hasOption(M7rCli.OPTION__DIRECTORY))
            throw new CliException("Option --directory not specified.");
        String directoryOptionValue = optionParserResultSpecific.getValue(M7rCli.OPTION__DIRECTORY);
        Path backupDirectory = Paths.get(directoryOptionValue);
        if (!Files.exists(backupDirectory))
            throw new CliException("Specified directory [" + backupDirectory.toAbsolutePath() + "] not found.");

        RecoverFileLocation recoverFileLocation = new RecoverFileLocation(backupDirectory);

        RecoverTherapist.exec(recoverFileLocation, this.cliContext);

        System.out.println("[OK] Recovered from backup.");
    }
}
