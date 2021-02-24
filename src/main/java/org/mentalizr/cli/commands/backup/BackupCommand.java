package org.mentalizr.cli.commands.backup;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.backup.*;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class BackupCommand implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        BackupFS backupFS = new BackupFS();

        System.out.println("Creating backup to [" + backupFS.getBackupDirAsString() + "].");

        try {
            BackupPrograms.exec(backupFS, cliContext);
            BackupTherapists.exec(backupFS, cliContext);
            BackupPatients.exec(backupFS, cliContext);
            BackupAccessKeys.exec(backupFS, cliContext);
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
