package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;

public class ShowConfigCommand extends CommandExecutor {

    public ShowConfigCommand(CliContext cliContext) {
        super(cliContext);
        this.checkInitStatus();
    }

    @Override
    public void execute() {
        CliConfiguration cliConfiguration = CliConfigurationLoader.load();
        System.out.println(cliConfiguration.toString());
    }
}
