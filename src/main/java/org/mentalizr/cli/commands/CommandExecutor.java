package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.config.Init;

import java.util.List;

public abstract class CommandExecutor {

    protected CliCallGlobalConfiguration cliCallGlobalConfiguration;
    protected List<String> commandList;
    protected OptionParserResult optionParserResultSpecific;
    protected CliConfiguration cliConfiguration;

    public CommandExecutor(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        this.cliCallGlobalConfiguration = cliCallGlobalConfiguration;
        this.commandList = commandList;
        this.optionParserResultSpecific = optionParserResultSpecific;
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

    public abstract void execute();

}
