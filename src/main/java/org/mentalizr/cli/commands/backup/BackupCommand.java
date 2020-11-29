package org.mentalizr.cli.commands.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.backup.*;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class BackupCommand extends CommandExecutor {

    public BackupCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        BackupFS backupFS = new BackupFS();

        System.out.println("Creating backup to [" + backupFS.getBackupDirAsString() + "].");

        BackupPrograms.exec(backupFS, this.cliContext);
        BackupTherapists.exec(backupFS, this.cliContext);
        BackupPatients.exec(backupFS, this.cliContext);
        BackupAccessKeys.exec(backupFS, this.cliContext);
    }

}
