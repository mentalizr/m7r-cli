package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.Init;

import java.util.List;

public class InitCommand extends CommandExecutor {

    public InitCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
    }

    @Override
    public void execute() {
        Init.init();
    }

}
