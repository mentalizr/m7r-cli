package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;

import java.util.List;

public class ShowConfigCommand extends CommandExecutor {

    public ShowConfigCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
        this.checkInitStatus();
    }

    @Override
    public void execute() {
        CliConfiguration cliConfiguration = CliConfigurationLoader.load();
        System.out.println(cliConfiguration.toString());
    }
}
