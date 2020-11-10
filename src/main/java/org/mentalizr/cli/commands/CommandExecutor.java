package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.config.Init;
import org.mentalizr.client.restServiceCaller.exception.RestServiceCallerConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceCallerHttpException;

public abstract class CommandExecutor {

    protected CliContext cliContext;

    protected CliConfiguration cliConfiguration;

    public CommandExecutor(CliContext cliContext) {
        this.cliContext = cliContext;
        this.cliConfiguration = null;
    }

    protected void checkedInit() {

        this.checkInitStatus();

        this.cliConfiguration = CliConfigurationLoader.load();
        if (!cliConfiguration.isValid()) {
            System.out.println("[Error] Configuration not valid. Mandatory 'server' configuration missing. Consider calling 'm7r config edit'.");
            System.exit(1);
        }

    }

    protected void checkInitStatus() {
        if (!Init.isInitialized()) {
            System.out.println("No configuration found! Consider calling 'm7r init'.");
            System.exit(1);
        }
    }

    public abstract void execute() throws RestServiceCallerHttpException, RestServiceCallerConnectionException;

}
