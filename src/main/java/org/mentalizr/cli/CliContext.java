package org.mentalizr.cli;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;

import java.util.List;

public class CliContext {

    private final CliCallGlobalConfiguration cliCallGlobalConfiguration;
    private final List<String> commandList;
    private final OptionParserResult optionParserResultSpecific;

    public CliContext(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        this.cliCallGlobalConfiguration = cliCallGlobalConfiguration;
        this.commandList = commandList;
        this.optionParserResultSpecific = optionParserResultSpecific;
    }

    public CliCallGlobalConfiguration getCliCallGlobalConfiguration() {
        return cliCallGlobalConfiguration;
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public OptionParserResult getOptionParserResultSpecific() {
        return optionParserResultSpecific;
    }
}
