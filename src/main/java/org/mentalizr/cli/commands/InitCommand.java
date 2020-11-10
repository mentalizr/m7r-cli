package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.config.Init;

public class InitCommand extends CommandExecutor {

    public InitCommand(CliContext cliContext) {
        super(cliContext);
    }

    @Override
    public void execute() {
        Init.init();
    }

}
