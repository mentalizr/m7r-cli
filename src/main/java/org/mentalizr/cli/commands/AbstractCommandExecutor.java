package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.config.Init;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public abstract class AbstractCommandExecutor {

    protected CliContext cliContext;

    public AbstractCommandExecutor(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    protected void checkedInit() {

        this.checkInitStatus();

        CliConfiguration cliConfiguration = CliConfigurationLoader.load();
        this.cliContext.setCliConfiguration(cliConfiguration);
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

    public abstract void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException;

}
