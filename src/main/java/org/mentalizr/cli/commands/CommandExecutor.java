package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;

import java.util.List;

public abstract class CommandExecutor {

    protected CliCallGlobalConfiguration cliCallGlobalConfiguration;
    protected List<String> commandList;
    protected OptionParserResult optionParserResultSpecific;

    public CommandExecutor(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        this.cliCallGlobalConfiguration = cliCallGlobalConfiguration;
        this.commandList = commandList;
        this.optionParserResultSpecific = optionParserResultSpecific;
    }

    public abstract void execute();

}
