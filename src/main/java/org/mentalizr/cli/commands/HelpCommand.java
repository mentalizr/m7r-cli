package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;

import java.util.List;

public class HelpCommand extends CommandExecutor {

    public HelpCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
    }

    @Override
    public void execute() {
        // TODO
        System.out.println("help: not implemented yet!");
    }
}
