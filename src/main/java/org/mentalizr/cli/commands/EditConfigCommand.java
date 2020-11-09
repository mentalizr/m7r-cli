package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;

import java.util.List;

public class EditConfigCommand extends CommandExecutor {

    public EditConfigCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
    }

    @Override
    public void execute() {
        // TODO
        System.out.println("Edit config. Not implemented yet.");
    }
}
