package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.config.Init;

public class CommandExecutorHelper {

    public static void checkedInit(CliContext cliContext) {

        checkInitStatus();

        CliConfiguration cliConfiguration = CliConfigurationLoader.load();
        cliContext.setCliConfiguration(cliConfiguration);
        if (!cliConfiguration.isValid()) {
            System.out.println("[Error] Configuration not valid. Mandatory 'server' configuration missing. Consider calling 'm7r config edit'.");
            System.exit(1);
        }
    }

    public static void checkInitStatus() {
        if (!Init.isInitialized()) {
            System.out.println("No configuration found! Consider calling 'm7r init'.");
            System.exit(1);
        }
    }

}
