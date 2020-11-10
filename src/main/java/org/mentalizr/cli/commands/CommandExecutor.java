package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ExitStatus;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.config.Init;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
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

    protected String checkedCall(RestService restService) {
        try {
            return RestServiceCaller.call(restService, cliConfiguration);

        } catch (RestServiceCallerConnectionException e) {
            System.out.println("[ERROR] " + e.getMessage());
            System.out.println("Cause: " + e.getCause().getMessage());
            if (this.cliContext.getCliCallGlobalConfiguration().isStacktrace()) {
                e.printStackTrace();
            }
            System.exit(ExitStatus.CONNECTION_ERROR);

        } catch (RestServiceCallerHttpException e) {
            switch (e.getStatusCode()) {
                case 401:
                    System.out.println("[ERROR] Authentication failed.");
                    System.exit(ExitStatus.HTTP_AUTHENTICATION_ERROR);
                default:
                    System.out.println("[ERROR] HttpError " + e.getStatusCode());
                    System.exit(ExitStatus.HTTP_OTHER_ERROR);
            }
        }

        throw new IllegalStateException();

    }

    public abstract void execute();

}
