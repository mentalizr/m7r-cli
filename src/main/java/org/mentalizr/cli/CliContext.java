package org.mentalizr.cli;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.CliConfiguration;

import java.util.List;

public class CliContext {

    private final CliCallGlobalConfiguration cliCallGlobalConfiguration;
    private final List<String> commandList;
    private final OptionParserResult optionParserResultSpecific;
    private CliConfiguration cliConfiguration;

    public CliContext(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        this.cliCallGlobalConfiguration = cliCallGlobalConfiguration;
        this.commandList = commandList;
        this.optionParserResultSpecific = optionParserResultSpecific;
        this.cliConfiguration = null;
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

    public void assertCliConfiguration() {
        if (this.cliConfiguration == null) {
            throw new IllegalStateException("CliConfiguration expected.");
        }
    }

    public CliConfiguration getCliConfiguration() {
        return cliConfiguration;
    }

    public void setCliConfiguration(CliConfiguration cliConfiguration) {
        this.cliConfiguration = cliConfiguration;
    }

}
